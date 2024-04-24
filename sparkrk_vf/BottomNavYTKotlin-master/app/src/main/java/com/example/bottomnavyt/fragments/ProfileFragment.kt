import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bottomnavyt.R

class ProfileFragment : Fragment() {

    private lateinit var peopleEditText: EditText
    private lateinit var roomsEditText: EditText
    private lateinit var totalLimitsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        peopleEditText = view.findViewById(R.id.peopleEditText)
        roomsEditText = view.findViewById(R.id.roomsEditText)
        totalLimitsTextView = view.findViewById(R.id.totalLimitsTextView)
        val calculateButton: Button = view.findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            calculateTotalLimits()
        }

        return view
    }

    private fun calculateTotalLimits() {
        val peopleStr = peopleEditText.text.toString()
        val roomsStr = roomsEditText.text.toString()

        if (peopleStr.isNotEmpty() && roomsStr.isNotEmpty()) {
            val people = peopleStr.toInt()
            val rooms = roomsStr.toInt()

            val electricityLimitPerPerson = 35 // kWh per person
            val waterLimitPerRoom = 500 // litres per room

            val totalElectricity = rooms * electricityLimitPerPerson
            val totalWater = people * waterLimitPerRoom

            totalLimitsTextView.text = "Target Monthly Limits:\nElectricity: $totalElectricity kWh\nWater: $totalWater litres"
        } else {
            totalLimitsTextView.text = "Please enter both the number of people and the number of rooms."
        }
    }
}
