import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val USER_TEXT_KEY = stringPreferencesKey("user_text")
    }

    val userTextFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TEXT_KEY] ?: ""
    }

    suspend fun saveUserText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TEXT_KEY] = text
        }
    }
}