package hu.bme.aut.movesy.utils

enum class PackageStatus {
    SENT{
        override fun displayName() = "Sent"
        override fun toBackandInt() = 0
        },
    IN_TRANSIT{
        override fun displayName() = "In transit"
        override fun toBackandInt() = 1
    },
    DELIVERED{
        override fun displayName() = "Delivered"
        override fun toBackandInt() = 2
    },
    WAITING_FOR_REVIEW{
        override fun displayName() = "Waiting for review"
        override fun toBackandInt() = 3
    };

    abstract fun displayName(): String
    abstract fun toBackandInt(): Int
}