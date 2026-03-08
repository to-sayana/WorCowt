# WorCowT – Complete AI Execution Plan

### Routine Improvement Gamified Android Application

---

# 1. Project Overview

**Project Name:** WorCowT
**Type:** Android Mobile Application
**Purpose:** Routine improvement and habit-building application
**Target Users:** Students and working professionals
**Platform:** Android
**Language:** Kotlin
**IDE:** Antigravity IDE
**Operating System (Development):** Kali Linux

WorCowT is a **gamified routine improvement mobile application** designed to help users build consistent daily habits such as drinking water, exercising, maintaining productivity schedules, taking medication, and maintaining sleep routines.

The application automatically **generates personalized routines** using onboarding data collected from the user. It tracks habit completion, calculates XP, manages streaks, and displays progress statistics.

The app uses a **cute kawaii cow mascot** to create an engaging and playful experience.

The goal of WorCowT is to **improve daily lifestyle discipline through gentle reminders and gamification**.

---

# 2. Core Philosophy

The application is designed around three main principles:

### Simplicity

The interface must remain minimal and intuitive.

### Consistency

Users are encouraged to maintain streaks.

### Gamification

XP, levels, and progress motivate the user.

---

# 3. Design Language

UI Style:
Minimal + Playful + Cute

Theme:
Light Mode Only

Primary Color
`#6BAEC6`

Secondary Color
`#CCCCCC`

Accent Colors
`#FDDEB2`
`#E36663`

Background should be soft and clean.

The mascot must be integrated naturally into UI components.

---

# 4. Mascot System

The application uses a **kawaii cow mascot**.

The mascot should appear in:

Landing screen
Workout page
Notifications
Achievement messages

Example motivational message:

"Moo! You're doing amazing today!"

The mascot assets will be provided by the developer in the **assets folder**.

When the system needs a mascot asset:

**Ask the developer to provide the required asset filename.**

---

# 5. Technology Stack

Platform: Android
Language: Kotlin

Backend: Supabase

Services used:

Supabase Authentication
Supabase Postgres Database
Supabase Realtime
Supabase Storage

Notifications: Android Notification Manager

Architecture Pattern:

MVVM (Model View ViewModel)

---

# 6. System Architecture

The system follows a **3 layer architecture**.

### Presentation Layer

Handles UI.

Includes:

Activities
Fragments
ViewModels

### Application Logic Layer

Handles:

Routine generation
XP calculation
Streak management
Notification scheduling

### Data Layer

Handles:

Supabase authentication
Database queries
Data persistence

---

# 7. Supabase Database Schema

The database must contain the following tables.

---

## users

Fields

user_id (uuid primary key)
name
email
age
role (student / working professional)
wake_time
sleep_time
water_goal
xp
level
current_streak
created_at

---

## routines

routine_id
user_id
task_name
category
scheduled_time
recurring
created_at

Categories include:

sleep
water
meal
medication
workout
productivity
custom

---

## task_logs

log_id
user_id
routine_id
completion_status
completion_time
xp_awarded

---

## statistics

user_id
tasks_completed
tasks_skipped
current_streak
longest_streak
xp_total

---

# 8. Onboarding System

When the user first launches the app:

Google authentication is triggered.

After login the onboarding questionnaire begins.

Maximum questions: 10

Questions include:

Name
Age
Student or working professional
Wake up time
Sleep time
Number of meals
Workout preference
Medication schedule
Daily water intake goal
Productivity preference

These answers will be used by the **Routine Generator Engine**.

---

# 9. Routine Generator Engine

The routine generator automatically creates a daily schedule.

Example:

Wake time: 7 AM

Generated routine:

7:00 Wake up
7:15 Drink water
8:00 Breakfast
9:00 Study/Work
1:00 Lunch
6:00 Workout
8:00 Dinner
10:30 Sleep

Rules:

User can edit tasks
User can delete tasks
User can add custom tasks

Routine must appear in a **timeline format**.

---

# 10. Timeline System

The home dashboard must display a **vertical timeline**.

Each task card includes:

Task name
Scheduled time
Status icon

Status options:

Complete
Snooze
Skip

Skipping or snoozing reduces XP.

---

# 11. Gamification System

Gamification encourages consistency.

XP rules:

Task completed → +10 XP
Daily routine completion → +50 XP
Weekly streak → +100 XP

Penalty rules:

Snooze → XP reduced
Skip → XP reduced

Level system:

Level 1 → 0 XP
Level 2 → 200 XP
Level 3 → 500 XP
Level 4 → 900 XP
Level 5 → 1500 XP

---

# 12. Streak System

The streak continues if:

Majority of tasks are completed.

The streak breaks if:

Most tasks are skipped.

Streak value is displayed in the dashboard.

---

# 13. Notification System

Notifications must be implemented using Android Notification Manager.

Reminder types:

Water reminder
Workout reminder
Medication reminder
Productivity reminder
Sleep reminder

Motivational notifications must appear occasionally.

Example:

"Moo! Keep going, your streak is growing!"

---

# 14. Navigation System

Bottom Navigation Bar Layout:

Button 1
About Developer (portfolio redirect)

Button 2
Home (center button)

Button 3
Routine

Button 4
Statistics

---

# 15. Screen List

The app must include the following screens.

Splash Screen
Landing Screen
Login Screen
Onboarding Questionnaire
Home Dashboard
Routine Page
Workout Page
Statistics Page
Profile Page
About Developer Page

---

# 16. Folder Structure

Recommended project structure:

```
app/
 ├── activities
 ├── fragments
 ├── viewmodels
 ├── models
 ├── repositories
 ├── services
 ├── utils
 ├── assets
 ├── layouts
```

---

# 17. Required Android Permissions

Internet
Notification permission
Exact alarm scheduling
Foreground service

---

# 18. Statistics System

Statistics page displays numerical values:

XP total
Current streak
Longest streak
Tasks completed
Tasks skipped

Charts are not required.

---

# 19. Required Project Documentation

The AI must also generate documentation required for the final year project.

---

## Synopsis

Must include:

Title
Abstract
Introduction
Problem Statement
Objectives
Scope
Methodology

---

## Abstract

Explain:

Purpose of WorCowT
Technology used
Expected outcomes

---

# 20. Diagrams Required

Generate diagrams for the project documentation.

Required diagrams:

ER Diagram
DFD Level 0
DFD Level 1
System Architecture Diagram
Use Case Diagram
Activity Diagram

---

# 21. Software Requirement Specification

The AI must generate a complete **SRS document** including:

Introduction
Overall description
Functional requirements
Non functional requirements
System architecture

---

# 22. Developer Interaction Protocol

If the AI requires any missing information it must ask the developer.

Examples:

Missing asset files
Mascot illustrations
UI images
Portfolio link

When such resources are needed the AI must output:

"Developer input required"

and clearly specify what is needed.

---

# 23. Development Strategy

The AI must build the project in phases.

Phase 1
Project initialization

Phase 2
Supabase authentication

Phase 3
Onboarding system

Phase 4
Routine generator

Phase 5
Timeline UI

Phase 6
Notifications

Phase 7
XP system

Phase 8
Statistics page

---

# 24. Error Handling

The system must handle:

Internet loss
Authentication failure
Database errors
Notification failures

Offline data must sync when internet returns.

---

# 25. Code Quality Rules

The AI must follow:

Clean architecture
Readable Kotlin code
Proper comments
Modular design

---

# 26. Performance Considerations

App must remain lightweight.

Avoid heavy background processes.

Notification scheduling must be efficient.

---

# 27. Security

Authentication must use Supabase secure tokens.

User data must be isolated per user.

---

# 28. Final Deliverables

The AI must produce:

Complete Android project
Supabase database schema
Documentation files
Project diagrams
Build instructions

---

# 29. Developer Assistance Instructions

Whenever the AI needs information it must ask the developer clearly.

Example:

"Please provide the mascot asset for the landing screen."

or

"Please provide your portfolio link for the developer page."

---

# 30. Final Objective

The final output must be a **fully functional Android habit building application with gamification and automated routine generation.**

The project must also include all documentation required for **BCA final year submission.**

The application must remain minimal, playful, and engaging.
