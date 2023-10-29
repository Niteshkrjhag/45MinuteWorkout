
package nitesh.mycompany.a45minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import nitesh.mycompany.a45minuteworkout.databinding.ActivityExerciseBinding
import nitesh.mycompany.a45minuteworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.Locale


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding?=null
    private var restTimer: CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress =0
    val restTimerDuration:Long = 1
    val exerciseTimerDuration:Long = 1

    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition =-1
    private var tts:TextToSpeech ?= null
    private var player:MediaPlayer ?=null

    private var exerciseAdapter:ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val toolbar: Toolbar = findViewById(R.id.toolbarExercise)
        setSupportActionBar(toolbar)

        // Enable the "up" navigation (back button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the title for the Toolbar
        supportActionBar?.title = "45Minute_Workout"

        // Define the behavior when the "up" button is pressed
        toolbar.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this,this)
        // binding?.flProgressbar?.visibility = View.GONE
        setupRestView()
        setupExerciseStatusRecyclerView()
    }


    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }


    private fun setupRestView(){

        try{
             val soundURI = Uri.parse("android.resource://nitesh.mycompany.45minuteworkout/" + R.raw.song)
        player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()
        }
        catch (e: Exception){
            e.printStackTrace()
        }


        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExericise?.visibility= View.INVISIBLE
        binding?.flExerciseView?.visibility= View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress =0
        }
        speakOut(("Take 10 second break!").toString())
        binding?.tvUpcomingExerciseName?.text=exerciseList!![currentExercisePosition+1].getName()
        SetRestProgressBar()
    }
    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.tvYes.setOnClickListener{
            customDialog.dismiss()
        }
        dialogBinding.tvYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun SetRestProgressBar(){

        binding?.progressBar?.progress=restProgress

        restTimer=object:CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10- restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setisSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
             setupExerciseView()
            }
        }.start()
    }
    private fun setupExerciseView(){
        player?.pause()
        player = null
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExericise?.visibility= View.VISIBLE
        binding?.flExerciseView?.visibility= View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExericise?.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++;
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()

            }
            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setisSelected(false)
                    exerciseList!![currentExercisePosition].setisCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{
                    finish()
                     val intent= Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()

    }

    override fun onDestroy(){

        if(restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        if (player!=null){
           player!!.stop()
        }
        super.onDestroy()
        binding = null
    }
    override fun onInit(status: Int) {

        //  After variable initializing set the language after a "success"ful result.)
        // START
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
        // END
    }
    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}