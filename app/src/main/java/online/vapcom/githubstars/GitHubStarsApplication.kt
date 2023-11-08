/*
 * (c) VAP Communications Group, 2023
 */
package online.vapcom.githubstars

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Main class of application, needed to start Koin
 */
class GitHubStarsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                //NOTE: set ERROR level due bug in Koin, see https://github.com/InsertKoinIO/koin/issues/1188
                if(BuildConfig.DEBUG) Level.ERROR else Level.NONE
            )

            androidContext(this@GitHubStarsApplication)
            modules(appModule)
        }
    }
}
