package com.example.codecompany_cryptotracker.domain

import kotlinx.serialization.Serializable


@Serializable
data class Asset(
    val asset_id: String?,
    val name: String?,
    val type_is_crypto: Int?,
    val data_quote_start: String?= null,
    val data_quote_end: String?= null,
    val data_orderbook_start: String?= null,
    val data_orderbook_end: String?= null,
    val data_trade_start: String?= null,
    val data_trade_end: String?= null,
    val data_symbols_count: Int?= null,
    val volume_1hrs_usd: Double?= null,
    val volume_1day_usd: Double?= null,
    val volume_1mth_usd: Double?= null,
    val id_icon: String?= null,
    val data_start: String?= null,
    val data_end: String?= null,
    val icon_url: String?= null
)
