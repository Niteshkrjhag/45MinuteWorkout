package nitesh.mycompany.a45minuteworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingjacks = ExerciseModel(
          1,"Jumping Jacks",R.drawable.ic_jumping_jacks,false,false
        )
        exerciseList.add(jumpingjacks)
        val abdominal_crunch = ExerciseModel(
            2,"Abdominal_Crunch",R.drawable.ic_abdominal_crunch,false,false
        )
        exerciseList.add(abdominal_crunch)
        val high_knees_running_in_place = ExerciseModel(
            3,"high_knees_running_in_place",R.drawable.ic_high_knees_running_in_place,false,false
        )
        exerciseList.add(high_knees_running_in_place)
        val lunge = ExerciseModel(
            4,"lunge",R.drawable.ic_lunge,false,false
        )
        exerciseList.add(lunge)
        val plank = ExerciseModel(
            5,"plank",R.drawable.ic_plank,false,false
        )
        exerciseList.add(plank)
        val pushup = ExerciseModel(
            6,"pushup",R.drawable.ic_push_up,false,false
        )
        exerciseList.add(pushup)
        val push_up_and_rotation=ExerciseModel(
            7,"push up and rotation",R.drawable.ic_push_up_and_rotation,false,false
        )
        exerciseList.add(push_up_and_rotation)
        val squat=ExerciseModel(
            8,"Squat",R.drawable.ic_squat,false,false
        )
        exerciseList.add(squat)
        val step_up_onto_chair=ExerciseModel(
            9,"step_up_onto_chair",R.drawable.ic_step_up_onto_chair,false,false
        )
        exerciseList.add(step_up_onto_chair)
        val triceps_dip_on_chair=ExerciseModel(
            10,"triceps_dip_on_chair",R.drawable.ic_triceps_dip_on_chair,false,false
        )
        exerciseList.add(triceps_dip_on_chair)
        val wall_sit=ExerciseModel(
            11,"wall_sit",R.drawable.ic_wall_sit,false,false
        )
        exerciseList.add(wall_sit)
        return exerciseList
    }
}