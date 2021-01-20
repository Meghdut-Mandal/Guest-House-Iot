package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.ui.main.RvItem
import androidx.paging.DataSource

class BookingDataSourceFactory : DataSource.Factory<Int, RvItem>() {

    private val dataSource = BookingDataSource()
    override fun create(): DataSource<Int, RvItem> = dataSource

}