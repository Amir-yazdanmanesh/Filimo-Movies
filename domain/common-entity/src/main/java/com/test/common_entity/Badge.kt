package com.test.common_entity


data class Badge(
    val free: Boolean,
    val backstage: Boolean,
    val vision: Boolean,
    val hear: Boolean,
    val online_release: Boolean,
    val exclusive: Boolean,
    val commingsoon: Boolean,
    val info: List<Info>
)