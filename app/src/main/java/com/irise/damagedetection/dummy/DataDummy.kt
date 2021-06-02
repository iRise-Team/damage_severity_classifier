package com.irise.damagedetection.dummy

import com.irise.damagedetection.R

object DataDummy {
    fun getData(): List<Dummy> {
        return listOf(
            Dummy(
                1,
                "laksmanayudha",
                R.drawable.test1,
                "Denpasar, Bali",
                "02-06-2021 16:59 UTC+8",
                "1"
            ),
            Dummy(
                2,
                "febraro",
                R.drawable.test2,
                "Badung, Bali",
                "02-06-2021 15:59 UTC+8",
                "0"
            ),
            Dummy(
                3,
                "wahyuwr7",
                R.drawable.test3,
                "Badung, Bali",
                "01-06-2021 10:19 UTC+8",
                "2"
            ),
            Dummy(
                4,
                "farinist",
                R.drawable.test4,
                "Badung, Bali",
                "01-06-2021 09:19 UTC+8",
                "1"
            ),
            Dummy(
                5,
                "dessyratna",
                R.drawable.test5,
                "Badung, Bali",
                "01-06-2021 09:19 UTC+8",
                "0"
            ),
            Dummy(
                6,
                "wahyuwr7",
                R.drawable.test6,
                "Denpasar, Bali",
                "31-05-2021 15:10 UTC+8",
                "2"
            ),
            Dummy(
                7,
                "febraro",
                R.drawable.test7,
                "Denpasar, Bali",
                "31-05-2021 12:10 UTC+8",
                "2"
            ),
            Dummy(
                8,
                "laksmanayudha",
                R.drawable.test6,
                "Gianyar, Bali",
                "31-05-2021 11:00 UTC+8",
                "2"
            )
        )
    }
}