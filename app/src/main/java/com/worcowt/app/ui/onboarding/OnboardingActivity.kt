package com.worcowt.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.worcowt.app.R
import com.worcowt.app.databinding.ActivityOnboardingBinding
import com.worcowt.app.ui.main.MainActivity
import com.worcowt.app.utils.Constants
import com.worcowt.app.utils.getAppPrefs
import com.worcowt.app.utils.showToast

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    private val answers = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        setupViewPager()
        observeViewModel()
    }

    private fun setupViewPager() {
        val questions = listOf(
            OnboardingQuestion(0, getString(R.string.q_name), QuestionType.TEXT),
            OnboardingQuestion(1, getString(R.string.q_age), QuestionType.NUMBER),
            OnboardingQuestion(2, getString(R.string.q_role), QuestionType.CHOICE,
                listOf(getString(R.string.role_student), getString(R.string.role_professional))),
            OnboardingQuestion(3, getString(R.string.q_wake_time), QuestionType.TIME),
            OnboardingQuestion(4, getString(R.string.q_sleep_time), QuestionType.TIME),
            OnboardingQuestion(5, getString(R.string.q_meals), QuestionType.NUMBER),
            OnboardingQuestion(6, getString(R.string.q_workout), QuestionType.CHOICE,
                listOf("Yes", "No")),
            OnboardingQuestion(7, getString(R.string.q_medication), QuestionType.CHOICE,
                listOf("Yes, morning", "Yes, evening", "Yes, both", "No")),
            OnboardingQuestion(8, getString(R.string.q_water), QuestionType.NUMBER),
            OnboardingQuestion(9, getString(R.string.q_productivity), QuestionType.CHOICE,
                listOf("Morning person", "Night owl", "Balanced"))
        )

        binding.viewPager.adapter = OnboardingAdapter(questions) { index, answer ->
            answers[index] = answer
        }
        binding.viewPager.isUserInputEnabled = false

        binding.dotsIndicator.text = "1 / ${questions.size}"

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.dotsIndicator.text = "${position + 1} / ${questions.size}"
                binding.btnBack.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
                binding.btnNext.text = if (position == questions.size - 1)
                    getString(R.string.onboarding_finish) else getString(R.string.onboarding_next)
            }
        })

        binding.btnNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < questions.size - 1) {
                binding.viewPager.currentItem = current + 1
            } else {
                submitOnboarding()
            }
        }

        binding.btnBack.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current > 0) binding.viewPager.currentItem = current - 1
        }
    }

    private fun submitOnboarding() {
        binding.btnNext.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        val name = answers[0] ?: ""
        val age = answers[1]?.toIntOrNull() ?: 0
        val role = if (answers[2] == getString(R.string.role_student)) "student" else "professional"
        val wakeTime = answers[3] ?: "07:00"
        val sleepTime = answers[4] ?: "23:00"
        val meals = answers[5]?.toIntOrNull() ?: 3
        val workout = answers[6] == "Yes"
        val medication = answers[7] ?: "No"
        val waterGoal = answers[8]?.toIntOrNull() ?: 8
        val productivity = when (answers[9]) {
            "Morning person" -> "morning"
            "Night owl" -> "night"
            else -> "balanced"
        }

        viewModel.saveOnboardingData(
            name, age, role, wakeTime, sleepTime,
            meals, workout, medication, waterGoal, productivity
        )
    }

    private fun observeViewModel() {
        viewModel.saveResult.observe(this) { success ->
            binding.progressBar.visibility = View.GONE
            binding.btnNext.isEnabled = true
            if (success) {
                getAppPrefs().edit()
                    .putBoolean(Constants.PREF_ONBOARDING_COMPLETE, true)
                    .apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                showToast(getString(R.string.error_generic))
            }
        }
    }
}

enum class QuestionType { TEXT, NUMBER, CHOICE, TIME }

data class OnboardingQuestion(
    val index: Int,
    val question: String,
    val type: QuestionType,
    val options: List<String> = emptyList()
)

class OnboardingAdapter(
    private val questions: List<OnboardingQuestion>,
    private val onAnswer: (Int, String) -> Unit
) : RecyclerView.Adapter<OnboardingAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        val tvQuestion = holder.view.findViewById<TextView>(R.id.tvQuestion)
        val inputLayout = holder.view.findViewById<TextInputLayout>(R.id.inputLayout)
        val etAnswer = holder.view.findViewById<TextInputEditText>(R.id.etAnswer)
        val radioGroup = holder.view.findViewById<RadioGroup>(R.id.radioGroup)
        val btnTimePicker = holder.view.findViewById<MaterialButton>(R.id.btnTimePicker)
        val tvSelectedTime = holder.view.findViewById<TextView>(R.id.tvSelectedTime)

        tvQuestion.text = question.question
        inputLayout.visibility = View.GONE
        radioGroup.visibility = View.GONE
        btnTimePicker.visibility = View.GONE
        tvSelectedTime.visibility = View.GONE

        when (question.type) {
            QuestionType.TEXT -> {
                inputLayout.visibility = View.VISIBLE
                etAnswer.inputType = android.text.InputType.TYPE_CLASS_TEXT
                etAnswer.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) onAnswer(question.index, etAnswer.text.toString())
                }
                etAnswer.doAfterTextChanged { onAnswer(question.index, it.toString()) }
            }
            QuestionType.NUMBER -> {
                inputLayout.visibility = View.VISIBLE
                etAnswer.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                etAnswer.doAfterTextChanged { onAnswer(question.index, it.toString()) }
            }
            QuestionType.CHOICE -> {
                radioGroup.visibility = View.VISIBLE
                radioGroup.removeAllViews()
                question.options.forEachIndexed { i, option ->
                    val rb = RadioButton(holder.view.context).apply {
                        id = View.generateViewId()
                        text = option
                        textSize = 16f
                        setPadding(8, 16, 8, 16)
                    }
                    radioGroup.addView(rb)
                }
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val rb = group.findViewById<RadioButton>(checkedId)
                    onAnswer(question.index, rb?.text?.toString() ?: "")
                }
            }
            QuestionType.TIME -> {
                btnTimePicker.visibility = View.VISIBLE
                tvSelectedTime.visibility = View.VISIBLE
                btnTimePicker.setOnClickListener {
                    val picker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(7)
                        .setMinute(0)
                        .setTitleText(question.question)
                        .build()
                    picker.addOnPositiveButtonClickListener {
                        val time = String.format("%02d:%02d", picker.hour, picker.minute)
                        tvSelectedTime.text = time
                        onAnswer(question.index, time)
                    }
                    val activity = holder.view.context as? AppCompatActivity
                    activity?.let { picker.show(it.supportFragmentManager, "timePicker") }
                }
            }
        }
    }

    override fun getItemCount() = questions.size

    private fun TextInputEditText.doAfterTextChanged(action: (String) -> Unit) {
        addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                action(s?.toString() ?: "")
            }
        })
    }
}
