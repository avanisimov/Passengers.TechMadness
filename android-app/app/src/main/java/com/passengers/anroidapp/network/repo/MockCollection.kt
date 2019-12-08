package com.passengers.anroidapp.network.repo

import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.model.FeedItemType
import org.threeten.bp.LocalDateTime

object MockCollection {

    val feedItems: List<FeedItem> by lazy {
        listOf(
                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef0",
                        title = "ПЛАТИ ЗА РУБЛЬ",
                        simpleDescription = "Почувствуй выгоду с новой акцией!",
                        fullDescription = "Осуществляйте платежи по специальному тарифу 1 рубль:\n" +
                        "На счета в сторонние банки со счета в рублях, поступившие с по\u00ADмощью сервиса Интернет Клиент-Банк;\n" +
                        "На счета в сторонние банки со счета в рублях с использованием сервиса срочного перевода Банка России, поступившие с помощью сервиса Интернет Клиент-Банк.\n" +
                        "Условия1 подключения к пакету:\n" +
                        "Наличие активного ключа Интернет Клиент-Банк;\n" +
                        "Поддержание необходимого остатка на расчетном счете в рублях на начало каждого операционного дня2.",
                        audienceId = "69597adc-00c6-48a8-aad5-3d0d9f3472b3",
                        date = LocalDateTime.now().minusHours(3L),
                        imageUrl = "https://api.rosbank.ru/uploads/slide/46/5d429ebbe1937.png",
                        type = FeedItemType.SPECIAL_DEAL
                ),

                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef1",
                        title = "СуперВЭД",
                        simpleDescription = "25% скидка на первые 3 месяца обслуживания по пакетному предложению",
                        fullDescription = "25% скидка на первые 3 месяца обслуживания по пакетному предложению",
                        audienceId = "69597adc-00c6-48a8-aad5-3d0d9f3472b3",
                        date = LocalDateTime.now().minusHours(35L),
                        imageUrl = "https://api.rosbank.ru/uploads/slide/10/5c9de84274ea7.jpg",
                        type = FeedItemType.SPECIAL_DEAL
                ),

                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef3",
                        title = "Выгодные пакеты для бизнеса",
                        simpleDescription = "Выбирайте свой пакет с учетом cферы деятельности и величины компании",
                        fullDescription = "Росбанк осуществляет открытие любых видов банковских счетов в рублях РФ и иностранной валюте для целей вашей компании. Если вам необходимо комплексное рациональное решение, вы можете выбрать свой пакет с учетом размера бизнеса.",
                        audienceId = "0189f6c3-8d6f-4561-beef-cef7338bed94",
                        date = LocalDateTime.now().minusHours(61L),
                        imageUrl = "https://api.rosbank.ru/uploads/slide/12/5c8cb0e2ca88c.jpg",
                        type = FeedItemType.SPECIAL_DEAL
                ),

                FeedItem(
                        id = "7bade105-4ae2-45e3-ac24-5293bae02ef4",
                        title = "Специальный счёт участника закупки",
                        simpleDescription = "Бесплатный счет для всех официальных площадок",
                        fullDescription = "С 1 октября 2018 года участники закупок в обеспечение заявок размещают денежные средства на специальном счёте участника закупки в уполномоченном банке.\n" +
                                "\n" +
                                "ПАО Росбанк включен в перечень из 18 уполномоченных банков в соответствии с распоряжением Правительства № 1451-Р от 13.07.2018.",
                        audienceId = "0189f6c3-8d6f-4561-beef-cef7338bed94",
                        date = LocalDateTime.now().minusHours(120L),
                        imageUrl = "https://api.rosbank.ru/uploads/slide/8/5c9de828bc98b.jpg",
                        type = FeedItemType.SPECIAL_DEAL
                )
        )
    }

    const val userID = 10498085L
}