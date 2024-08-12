package com.example.valuebetsapp

import android.util.Log
import org.jsoup.Jsoup

class BetsRepositoryImpl : BetsRepository {

    fun extractValueFromUrl(url: String): String? {
        val startIndex = url.indexOf('=') + 1
        val endIndex = url.indexOf('&', startIndex)
        if (startIndex != -1 && endIndex != -1) {
            return url.substring(startIndex, endIndex)
        }
        return null
    }
    override fun getBets(): MutableList<Bet> {
        val listData = mutableListOf<Bet>()

        try {
            val doc = Jsoup.connect(Constants.url).get()
            val bets = doc.select(".app-table.app-wide tbody")
            val betsSize = bets.size

            for(i in 0 until betsSize){
                val id = i
                val bookie = "22bet"
                val sport = bets.select("tr .booker.booker-first .minor").eq(i).text()
                val time = bets.select(".time").eq(i).text()
                val event = bets.select(".event").eq(i).text()
                val market = bets.select(".coeff").eq(i).text()
                val odd = bets.select(".value").eq(i).text()
                val value = bets.select("tr td:nth-child(8)").eq(i).text()
                var link =""
                val linkElement = bets.select(".extra").eq(i)
                if (linkElement.hasText()) {
                     link = linkElement.select("a").attr("href")
                }

                if (Constants.phrasesToExcludeLeague.any { event.contains(it, ignoreCase = true) }) {
                    continue
                }

                if (link.isNotEmpty()){
                    val completeLink = Constants.LINK_1_EVENET + extractValueFromUrl(link) + Constants.LINK_2_EVENT
                    Log.i("complete link eventu", i.toString() + event + " : " + completeLink)
                    val docNew =  Jsoup.connect(completeLink).get()
                    val additionalBets = docNew.select(".app-table.app-wide tbody")
                    val additionalBetsSize = additionalBets.size
                    for (j in 0 until additionalBetsSize){
                        val bookieNew = "22bet"
                        val sportNew = additionalBets.select("tr .booker.booker-first .minor").eq(j).text()
                        val timeNew = additionalBets.select(".time").eq(j).text()
                        val eventNew = additionalBets.select(".event").eq(j).text()
                        val marketNew = additionalBets.select(".coeff").eq(j).text()
                        if (Constants.phrasesToExclude.any { marketNew.contains(it, ignoreCase = true) }) {
                            continue
                        }
                        val oddNew = additionalBets.select(".value").eq(j).text()
                        val oddValue = oddNew.toFloatOrNull() ?: continue
                        if (oddValue > 6.0) {
                            continue
                        }
                        val valueNew = additionalBets.select("tr td:nth-child(8)").eq(j).text()
                        val bet = Bet(id,bookieNew,sportNew,timeNew,eventNew,marketNew,oddNew,valueNew)
                        Log.i("bet dodatkowy", bet.toString())
                        listData.add(bet)
                    }
                }

                if (Constants.phrasesToExclude.any { market.contains(it, ignoreCase = true) }) {
                    continue
                }

                val bet = Bet(id,bookie,sport,time,event,market,odd,value)
                Log.i("bet normalny", bet.toString())
                listData.add(bet)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listData
    }
}