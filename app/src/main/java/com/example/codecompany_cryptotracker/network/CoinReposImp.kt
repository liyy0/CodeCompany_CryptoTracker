package com.example.codecompany_cryptotracker.network

import com.example.codecompany_cryptotracker.data.model.CoinData
import com.example.codecompany_cryptotracker.data.model.CoinName
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNews
import com.example.codecompany_cryptotracker.data.model.CoinTickerData
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CoinReposImp(
    private val api: ApiInterface
): CoinRepos{
    companion object{
        const val API_KEY = "CG-3LG1Vd4vgC9BPpNjDAdYXLNb"
        const val API_KEY_News = "e567bf85a2b94579b445aeda638bdeb5"
    }

        override suspend fun getAllCoinList(
            currency: String,
            locale: String,
            ids: String?
        ): Flow<Result<List<CoinNameItem>>> {
            return flow {
                val productsFromApi = try {
                    api.getAllCoinName(API_KEY, currency, locale, ids)
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Result.Error(message = e.stackTraceToString()))
                    return@flow
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Result.Error(message = e.stackTraceToString()))
                    return@flow
                }  catch (e: Exception) {
                    e.printStackTrace()
                    emit(Result.Error(message = e.stackTraceToString()))
                    return@flow
                }
                emit(Result.Success(productsFromApi))
            }
        }

    override suspend fun getCoinDataById(
        id: String,
        communityData: Boolean,
        developerData: Boolean
    ): Flow<Result<CoinData>> {
        return flow {
            val productsFromApi = try {
                api.getCoinDataById(id, API_KEY, communityData, developerData)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(productsFromApi))
        }
    }

    override suspend fun getCoinTickerDataById(
        id: String,
        exchangeIds: String?,
        includeExchangeLogo: Boolean,
        page: Int?,
        order: String?,
        depth: Boolean
    ): Flow<Result<CoinTickerData>> {
        return flow {
            val productsFromApi = try {
                api.getTickersById(id, API_KEY,exchangeIds, includeExchangeLogo, page, order, depth)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(productsFromApi))
        }
    }

    override suspend fun getMarkectChartData(
        id: String,
        currency: String,
        days: Int,
        interval: String
    ): Flow<Result<MarketChartDataModel>> {
        return flow {
            val productsFromApi = try {
                api.getMarketChart(id, currency, days, interval, API_KEY)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(productsFromApi))
        }
    }

    override suspend fun getCoinNews(
        coinName: String,
        from: String,
        to: String,
        language: String,
        pageSize: Int,
        page: Int,
        sortBy: String
    ): Flow<Result<CoinNews>> {
        return flow {
            val productsFromApi = try {
                api.getCoinNews(API_KEY_News, coinName, from, to, language, pageSize, page, sortBy)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(productsFromApi))
        }
    }

}