package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.ui.main.RvItem
import androidx.paging.DataSource

class BookingDataSourceFactory(startTime: Long) : DataSource.Factory<Int, RvItem>() {

    constructor() : this(-1)

    private val dataSource = BookingDataSource(startTime)
    override fun create(): DataSource<Int, RvItem> = dataSource

}