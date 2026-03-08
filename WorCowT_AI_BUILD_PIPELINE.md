# WorCowT AI Build Pipeline

## Autonomous Development Workflow for AI IDE

---

# 1. Purpose of This Document

This document defines the **step-by-step autonomous workflow** for the AI development environment to build the **WorCowT Android application**.

The AI must follow this workflow strictly and complete each phase sequentially.

The goal is to ensure:

• predictable development
• modular architecture
• stable integration
• minimal developer interruptions

---

# 2. AI Role Definition

The AI agent must behave as:

**Senior Android Engineer + Backend Architect + Technical Writer**

Responsibilities include:

• writing Kotlin code
• structuring Android architecture
• integrating Supabase
• implementing notifications
• generating documentation
• generating diagrams for the BCA project

The AI must **never invent assets or missing files**.

If information is missing the AI must ask the developer.

---

# 3. Project Overview

Application Name: **WorCowT**

Application Type:
Gamified routine improvement application

Platform:
Android

Language:
Kotlin

IDE:
Antigravity IDE

Backend:
Supabase

Database:
PostgreSQL (Supabase)

Authentication:
Google OAuth via Supabase

Architecture Pattern:

MVVM

---

# 4. Global Development Rules

The AI must obey these rules:

1. Never skip phases.
2. Never generate incomplete modules.
3. Always confirm build success after each phase.
4. Always maintain clean architecture.
5. Always comment code clearly.
6. Never assume missing assets.
7. Ask the developer when resources are required.

---

# 5. Project Development Phases

The project must be built in **10 structured phases**.

Each phase has:

• goal
• deliverables
• validation step

---

# PHASE 1

## Project Initialization

Goal:

Create the Android project structure.

Tasks:

1 Initialize Kotlin Android project
2 Configure Gradle dependencies
3 Setup MVVM architecture folders
4 Configure project theme
5 Add navigation framework

Dependencies required:

Kotlin
AndroidX
Material UI

Deliverables:

• Base Android project
• Folder architecture
• Empty main activity

Validation:

The project must compile successfully.

---

# PHASE 2

## Supabase Integration

Goal:

Connect the application to Supabase backend.

Tasks:

1 Setup Supabase SDK
2 Configure environment variables
3 Connect to Supabase project
4 Setup authentication module

Authentication method:

Google OAuth

Deliverables:

• login functionality
• user session management

Developer Input Required:

Supabase URL
Supabase public key

Validation:

User can successfully login.

---

# PHASE 3

## Database Schema Creation

Goal:

Create database tables required for the application.

Tables required:

users
routines
task_logs
statistics

Tasks:

1 Write SQL schema
2 Deploy schema to Supabase
3 Create data access repository layer

Deliverables:

• Supabase database ready
• repository layer implemented

Validation:

Data insertion and retrieval must work.

---

# PHASE 4

## Onboarding System

Goal:

Collect user routine preferences.

Tasks:

1 Build onboarding UI
2 Implement question flow
3 Save responses to Supabase

Questions include:

Name
Age
Student or working professional
Wake time
Sleep time
Meals per day
Workout preference
Medication timing
Water intake goal
Productivity preference

Deliverables:

• onboarding screens
• stored user profile

Validation:

User onboarding must complete successfully.

---

# PHASE 5

## Routine Generation Engine

Goal:

Automatically create routines based on onboarding data.

Tasks:

1 Implement routine generator algorithm
2 Generate default daily schedule
3 Store routines in database

Routine categories:

sleep
water
meal
medication
workout
productivity
custom

Deliverables:

• routine generation engine
• stored routine list

Validation:

Routine appears correctly after onboarding.

---

# PHASE 6

## Timeline UI

Goal:

Display routines in a timeline interface.

Tasks:

1 Build vertical timeline layout
2 Render daily tasks
3 Add task interaction buttons

User actions:

Complete
Snooze
Skip

Deliverables:

• interactive timeline

Validation:

Task status updates must persist.

---

# PHASE 7

## Notification System

Goal:

Send reminders to users.

Tasks:

1 Implement notification manager
2 Schedule alarms for tasks
3 Trigger reminders

Reminder types:

water
workout
sleep
medication
productivity

Deliverables:

• working notifications

Validation:

Notifications appear at scheduled times.

---

# PHASE 8

## XP and Streak System

Goal:

Implement gamification.

XP rules:

Task completed → +10 XP
Full routine → +50 XP
Weekly streak → +100 XP

Penalty:

Snooze reduces XP
Skip reduces XP

Tasks:

1 Implement XP calculator
2 Implement streak tracker

Deliverables:

• XP system
• streak system

Validation:

XP increases correctly.

---

# PHASE 9

## Statistics Module

Goal:

Display habit statistics.

Tasks:

Calculate:

tasks completed
tasks skipped
current streak
XP total

Display values numerically.

Deliverables:

Statistics page

Validation:

Stats must reflect user behavior.

---

# PHASE 10

## Final UI and Mascot Integration

Goal:

Integrate mascot visuals.

Tasks:

1 Add mascot assets
2 Display mascot on screens
3 Add motivational messages

Developer Input Required:

Mascot image assets

Deliverables:

Complete polished UI

Validation:

All screens display correctly.

---

# 6. Navigation Structure

Bottom Navigation:

About Developer
Home
Routine
Statistics

Top Navigation:

Notifications
Profile

---

# 7. Testing Strategy

The AI must implement:

Unit testing
UI testing
Notification testing

---

# 8. Error Handling

Handle these cases:

No internet
Database timeout
Login failure

The application must remain stable.

---

# 9. Documentation Generation

The AI must also generate project documentation.

Required outputs:

Synopsis
Abstract
Introduction
Problem statement
Objectives
Scope

Technical diagrams:

ER diagram
DFD
Use case diagram
System architecture

---

# 10. Developer Interaction Protocol

When developer input is needed the AI must output:

"Developer input required"

Examples:

Supabase credentials
Mascot assets
Portfolio link

The AI must **never proceed with assumptions**.

---

# 11. Build Completion Criteria

The project is considered complete when:

• Android application compiles
• login works
• routine generation works
• reminders work
• XP system works
• statistics appear
• documentation generated

---

# 12. Final Output

The final result must include:

Complete Android application
Supabase backend schema
Full project documentation
System diagrams
Build instructions
