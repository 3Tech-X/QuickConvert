package com.three.tech.quickconvert.networking.dataclass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("base_code")
    val baseCode: String,
    @SerialName("conversion_rate")
    val conversionRate: Double,
    @SerialName("conversion_result")
    val conversionResult: Double,
    @SerialName("result")
    val result: String,
    @SerialName("target_code")
    val targetCode: String,
)