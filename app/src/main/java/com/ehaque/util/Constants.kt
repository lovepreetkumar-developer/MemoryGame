package com.ehaque.util

import com.ehaque.R

object Constants {

    const val DEVICE_ID = "device_id"
    const val ORDER_ID = "order_id"
    const val USER_LOGIN_TYPE = "userLoginType"
    const val SHOW_LOCK_SCREEN = "show_lock_screen"
    const val REMEMBER = "remember"
    const val FIREBASE_TOKEN = "firebase_token"
    const val NOTIFICATION = "notification"
    const val TIMEZONE = "timezone"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longtitude"
    const val LANGUAGE = "language"
    const val RATE_COUNT = "rate_count"
    const val CURRENT_DATE = "current_date"
    const val MEMBER_LOGGED_IN = "member_logged_in"
    const val AGREEMENT_HAS_SEEN = "agreement_has_seen"
    const val PAYMENT_INSTRUCTIONS = "payment_instructions"
    const val SOUND_ENABLED = "sound_enabled"
    const val WON_COUNT = "won_count"
    const val LOSE_COUNT = "lose_count"
    const val ATTEMPTS_COUNT = "attempts_count"

    val GAME_TYPES = arrayOf(
        "4 Cards Play",
        "6 Cards Play",
        "8 Cards Play",
        "10 Cards Play"
    )

    val GAME_TYPES_CARDS = arrayOf(
        4,
        6,
        8,
        10
    )

    val CARD_IMAGES_DECK_10 = arrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_5,
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_5
    )

    val CARD_TAGS_DECK_10 = arrayOf(
        "card_1",
        "card_2",
        "card_3",
        "card_4",
        "card_5",
        "card_1",
        "card_2",
        "card_3",
        "card_4",
        "card_5"
    )

    val CARD_IMAGES_DECK_8 = arrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
    )

    val CARD_TAGS_DECK_8 = arrayOf(
        "card_1",
        "card_2",
        "card_3",
        "card_4",
        "card_1",
        "card_2",
        "card_3",
        "card_4"
    )

    val CARD_IMAGES_DECK_6 = arrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3
    )

    val CARD_TAGS_DECK_6 = arrayOf(
        "card_1",
        "card_2",
        "card_3",
        "card_1",
        "card_2",
        "card_3"
    )

    val CARD_IMAGES_DECK_4 = arrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_1,
        R.drawable.card_2
    )

    val CARD_TAGS_DECK_4 = arrayOf(
        "card_1",
        "card_2",
        "card_1",
        "card_2"
    )
}