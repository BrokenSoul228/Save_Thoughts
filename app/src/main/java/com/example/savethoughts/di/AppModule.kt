package com.example.savethoughts.di

import dagger.Component

@Component
interface AppComponent {

}
//@Module
//class AppModule(private val application: Application) {
//
//    @Provides
//    @Singleton
//    fun provideContext() : Context = application.applicationContext
//
//    @Provides
//    @Singleton
//    fun roomBase(context: Context) : DataBase = DataBase.getDatabase(context)
//
//
//
//}