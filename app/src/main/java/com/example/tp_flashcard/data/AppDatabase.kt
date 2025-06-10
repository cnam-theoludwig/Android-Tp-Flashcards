package com.example.tp_flashcard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.model.FlashCardCategory

@Database(entities = [FlashCard::class, FlashCardCategory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flashcard_database"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val repository = FlashcardRepository.getInstance(getDatabase(context).flashcardDao())
                            repository.prepopulateDatabase()
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
