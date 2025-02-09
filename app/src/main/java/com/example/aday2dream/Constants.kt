package com.example.aday2dream

import com.google.android.apps.common.testing.accessibility.framework.BuildConfig

const val BASE_URL = "https://192.168.88.20:8443"

const val LOG_IN = "/accounts/login"
const val REGISTER = "/accounts/register"
const val GET_PROFILE = "/accounts/profile"
const val ACCOUNT_BY_ID = "/accounts/{id}"
const val LOG_OUT = "/accounts/logout"


const val GET_POSTS = "/posts"
const val CREATE_POST = "/posts"
const val POST_BY_ID = "/posts/{id}"
const val UPDATE_POST = "/posts/{id}"
const val POSTS_BY_ACCOUNT = "/posts/publisher/{publisherId}"


const val UPLOAD_AUDIOFILE = "/audiofiles/upload"
const val GET_AUDIOFILE = "/audiofiles/{id}"

const val PROMPT_BY_ID = "/prompts/{id}"
const val CREATE_PROMPT = "/prompts"
const val PROMPTS_BY_POST_ID = "/prompts/post/{postId}"
const val PROMPTS_BY_ACCOUNT_ID = "/prompts/account/{accountId}"
const val SEND_EMAIL = "email/send"