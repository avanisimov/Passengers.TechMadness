package com.passengers.anroidapp.network.repo

import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.model.FeedItemType
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

object MockCollection {

    val feedItems: List<FeedItem> by lazy {
        listOf(
                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef0",
                        title = "ЦБ предложил усложнить выдачу ипотеки закредитованным заемщикам",
                        content = "Регулятор намерен сократить интерес банков к выдаче ипотеки уже закредитованным клиентам и тем самым снизить риски дефолтов. Но инициатива может замедлить рост рынка и негативно повлиять на застройщиков, предупреждают эксперты\n" +
                                "\n" +
                                "Подробнее на РБК:\n" +
                                "https://www.rbc.ru/finances/02/12/2019/5de546fb9a794723ae57ed28",
                        date = LocalDateTime.now().minusHours(3L),
                        imageUrl = "https://s0.rbk.ru/v6_top_pics/resized/1180xH/media/img/7/07/755753154647077.jpeg",
                        type = FeedItemType.NEWS
                ),
                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef1",
                        title = "ЦБ предложил усложнить выдачу ипотеки закредитованным заемщикам.........",
                        content = "Регулятор намерен сократить интерес банков к выдаче ипотеки уже закредитованным клиентам и тем самым снизить риски дефолтов. Но инициатива может замедлить рост рынка и негативно повлиять на застройщиков, предупреждают эксперты\n" +
                                "\n" +
                                "Подробнее на РБК:\n" +
                                "https://www.rbc.ru/finances/02/12/2019/5de546fb9a794723ae57ed28",
                        date = LocalDateTime.now().minusHours(4L),
                        imageUrl = "https://s0.rbk.ru/v6_top_pics/resized/1180xH/media/img/7/07/755753154647077.jpeg",
                        type = FeedItemType.NEWS
                )
        )
    }

    val userID = 10498085L
}