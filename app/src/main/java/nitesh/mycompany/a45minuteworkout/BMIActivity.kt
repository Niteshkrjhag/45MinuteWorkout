package nitesh.mycompany.a45minuteworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import nitesh.mycompany.a45minuteworkout.databinding.ActivityBmiBinding
import java.lang.Math.pow
import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

     companion object{
      private const val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
         private const val US_UNIT_VIEW = "US_UNIT_VIEW"
    }

    var binding:ActivityBmiBinding?=null

    private var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        // Set up the Toolbar as the ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar_bmi_activity)
        setSupportActionBar(toolbar)

        // Enable the "up" navigation (back button)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Define the behavior when the "up" button is pressed
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding?.rgUnits?.setOnCheckedChangeListener{_,checkedId:Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }

        }


        binding?.btnCalculateUnits?.setOnClickListener {
               calculateUnits()
        }
    }
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNIT_VIEW // Current View is updated here.
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // make weight view visible.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE // make height feet view visible.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE // make height inch view visible.

        binding?.etUsMetricUnitWeight?.text!!.clear() // weight value is cleared.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // height feet value is cleared.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // height inch is cleared.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNIT_VIEW // Current View is updated here.
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE // METRIC  Height UNITS VIEW is Visible
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE // make weight view Gone.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE // make height feet view Gone.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE // make height inch view Gone.

        binding?.etMetricUnitHeight?.text!!.clear() // height value is cleared if it is added.
        binding?.etMetricUnitWeight?.text!!.clear() // weight value is cleared if it is added.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){
        var bmiLabel:String ?=null
        var bmiDescription : String ?=null
        if(bmi<=18.5f){
            bmiLabel= "Very severely underweight"
            bmiDescription = "Oops, you really need to take better care of yourself, eat better"
        }
        else if(bmi>=18.5f && bmi<=24.9f){
            bmiLabel= "Normal Weight"
            bmiDescription = ""
        }
        else if(bmi>=25f && bmi<=29.9f){
            bmiLabel= "Overweight"
            bmiDescription = "Oops, you really need to take better care of yourself, eat better"
        }
        else if(bmi>=30f){
            bmiLabel= "Obesity"
            bmiDescription = "Oops, you really need to take better care of yourself, eat better"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIValue?.text= bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text= bmiDescription
        binding?.llDiplayBMIResult?.visibility =View.VISIBLE
    }
    private fun validateMetricUnits(): Boolean {
        var isValid = true
        when{
            binding?.etMetricUnitWeight?.text.toString().isEmpty() ->{
                isValid = false
            }
            binding?.etMetricUnitHeight?.text.toString().isEmpty() ->{
                isValid = false
            }
        }
        return isValid
    }
    private fun validateUsMetricUnits(): Boolean {
        var isValid = true
        when {
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
            isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()-> {
            isValid = false
            }
    }
        return isValid
    }
    private fun calculateUnits() {
        if(currentVisibleView== METRIC_UNIT_VIEW){
            if(validateMetricUnits()){
                val heightValue:Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue:Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val BMI = weightValue/(heightValue*heightValue)
                displayBMIResult(BMI)
            }else{
                Toast.makeText(this,"Please enter valid values",Toast.LENGTH_SHORT).show()
            }
        }else{
            if(validateUsMetricUnits()){
                val usUnitHeightValueFeet:Float =
                    binding?.etUsMetricUnitHeightFeet?.text.toString().toFloat()
                val usUnitHeightValueInch:Float =
                    binding?.etUsMetricUnitHeightInch?.text.toString().toFloat()
                val usUnitWeightValue:Float =
                    binding?.etUsMetricUnitWeight?.text.toString().toFloat()
                val usBMI = (usUnitWeightValue /(pow((usUnitHeightValueFeet*12+usUnitHeightValueInch).toDouble(),2.0)))*703
                displayBMIResult(usBMI.toFloat())
            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_SHORT).show()
            }
        }


    }
}