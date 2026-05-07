package com.example.laboratory5

import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

class MainActivity : FragmentActivity() // Inherits from FragmentActivity
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent{
            MainScreen(onSwitchFragment = { id -> switchFragment(id) })
        }
    }

    private fun switchFragment(id: Int)
    {
        // CHANGE FRAGMENT 1/2
        val fragment = if (id == 1) Fragment1() else Fragment2()
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }
}



// landscape or portrait
@Composable
fun MainScreen(onSwitchFragment: (Int) -> Unit)
{
    val config = LocalConfiguration.current

    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        LandscapeLayout(onSwitchFragment) // landscape
    else
        PortraitLayout(onSwitchFragment) // portrait
}

@Composable
fun PortraitLayout(onSwitchFragment: (Int) -> Unit)
{
    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        AndroidView(
            factory = { context -> FrameLayout(context).apply { id = R.id.fragment_container } },
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onSwitchFragment(1) }, modifier = Modifier.fillMaxWidth())
        {
            Text(stringResource(R.string.btn_fragment_1))
        }
        Button(onClick = { onSwitchFragment(2) }, modifier = Modifier.fillMaxWidth())
        {
            Text(stringResource(R.string.btn_fragment_2))
        }
    }
}

@Composable
fun LandscapeLayout(onSwitchFragment: (Int) -> Unit)
{
    Row(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        AndroidView(
            factory = { context -> FrameLayout(context).apply { id = R.id.fragment_container } },
            modifier = Modifier.weight(1f).fillMaxHeight()
        )
        Column(modifier = Modifier.width(200.dp).padding(start = 16.dp)) {
            Button(onClick = { onSwitchFragment(1) }) { Text(stringResource(R.string.btn_fragment_1)) }
            Button(onClick = { onSwitchFragment(2) }) { Text(stringResource(R.string.btn_fragment_2)) }
        }
    }
}