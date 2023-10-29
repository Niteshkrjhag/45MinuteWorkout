package nitesh.mycompany.a45minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import nitesh.mycompany.a45minuteworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar_history_activity)
        setSupportActionBar(toolbar)

        // Enable the "up" navigation (back button)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Define the behavior when the "up" button is pressed
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }



    }
}