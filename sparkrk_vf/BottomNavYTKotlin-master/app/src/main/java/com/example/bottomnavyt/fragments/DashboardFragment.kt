import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bottomnavyt.R
import com.example.bottomnavyt.database.DatabaseHelper

import com.google.android.material.progressindicator.CircularProgressIndicator

class DashboardFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 2000 // Update every 2 seconds
    private val MAX_WATER_USAGE = 5.0 // Max water usage in litres
    private val MAX_ELECTRICITY_USAGE = 550.0 // Max electricity usage in watts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        // Update current water usage and progress bar
        updateWaterUsage(view)

        // Update current electricity usage and progress bar
        updateElectricityUsage(view)

        // Update total water and electricity usage
        updateTotalWaterAndElectricityUsage(view)

        // Start updating data every 2 seconds
        startDataUpdate(view)
    }

    private fun startDataUpdate(view: View) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Insert random data into database
                databaseHelper.insertRandomData()

                // Update current water usage and progress bar
                updateWaterUsage(view)

                // Update current electricity usage and progress bar
                updateElectricityUsage(view)

                // Update total water and electricity usage
                updateTotalWaterAndElectricityUsage(view)

                // Schedule next update
                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)
    }

    private fun updateWaterUsage(view: View) {
        // Retrieve current water usage from database
        val currentWaterUsage = databaseHelper.getCurrentWaterUsage().toDouble()
        // Update text view
        val currentWaterValueTextView = view.findViewById<TextView>(R.id.currentWaterValueTextView)
        currentWaterValueTextView.text = String.format("%.2f", currentWaterUsage)

        // Update progress bar
        val waterProgressBar = view.findViewById<CircularProgressIndicator>(R.id.waterProgressBar)
        val progress = ((currentWaterUsage / MAX_WATER_USAGE) * 100).toInt()
        waterProgressBar.progress = progress
    }

    private fun updateElectricityUsage(view: View) {
        // Retrieve current electricity usage from database
        val currentElectricityUsage = databaseHelper.getCurrentElectricityUsage() / 0.000555556 // converting to kilowatts
        // Update text view
        val currentElectricityValueTextView = view.findViewById<TextView>(R.id.currentElectricityValueTextView)
        currentElectricityValueTextView.text = String.format("%.2f", currentElectricityUsage)

        // Update progress bar
        val electricityProgressBar = view.findViewById<CircularProgressIndicator>(R.id.electricityProgressBar)
        val progress = ((currentElectricityUsage / MAX_ELECTRICITY_USAGE) * 100).toInt()
        electricityProgressBar.progress = progress
    }

    private fun updateTotalWaterAndElectricityUsage(view: View) {
        val totalWaterUsage = databaseHelper.getTotalWaterUsage()
        val totalElectricityUsage = databaseHelper.getTotalElectricityUsage() / 1000 // Convert to kilowatts

        val totalWaterUsageTextView = view.findViewById<TextView>(R.id.totalWaterUsageTextView)
        val totalElectricityUsageTextView = view.findViewById<TextView>(R.id.totalElectricityUsageTextView)

        totalWaterUsageTextView.text = "Total Water Usage: %.2f Litres".format(totalWaterUsage)
        totalElectricityUsageTextView.text = "Total Electricity Usage: %.4f KWH".format(totalElectricityUsage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null)
    }
}
