package com.example.downloadworker.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.downloadworker.model.Converter
import com.example.downloadworker.model.Product



@Database(entities = arrayOf(Product::class), version = 1 )
@TypeConverters(Converter::class)
abstract class ProductDB: RoomDatabase() {

    abstract fun GetProductDao(): ProductDao
    companion object{
        @Volatile
        private var INSTANCE: ProductDB? = null
        fun getInstance (ctx: Context): ProductDB{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, ProductDB::class.java, "product_database")
                    .build()
                INSTANCE = instance
                // return instance
                instance }
        }
    }

}