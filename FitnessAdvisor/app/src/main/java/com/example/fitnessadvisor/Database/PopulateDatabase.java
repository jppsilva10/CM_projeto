package com.example.fitnessadvisor.Database;

import com.example.fitnessadvisor.R;
import com.example.fitnessadvisor.TaskManager;
import com.example.fitnessadvisor.WorkoutTaskManager;

public class PopulateDatabase {

    public static void populateExercises(AppDatabase db, WorkoutTaskManager taskManager){
        Exercise exercise = new Exercise();
        exercise.name = "Barbell Bench Press";
        exercise.description = "Lying on a flat bench place your hands up on the bar griping it slightly wider than shoulder width apart. Place your feet on the ground in line with or behind your knees. Create an arch in your lower back and push your chest up. Lift the bar up off the rack so that the bar is above your chest. Lower the bar directly down to the chest with your elbow pointing at approximately 45 degrees. Once the bar touches the chest, pause, and then press directly up again. Continue until all reps are completed and then re-rack the barbell.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_bench_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Decline Barbell Bench Press";
        exercise.description = "Lying on the bench place your hands up on the bar griping it slightly wider than shoulder width apart. Place your legs under the leg support. Create an arch in your lower back and push your chest up. Lift the bar up off the rack so that the bar is above your chest. Lower the bar directly down to the chest with your elbow pointing at approximately 45 degrees. Once the bar touches the chest, pause, and then press directly up again, exhaling your breath. Continue until all reps are completed and then re-rack the barbell.";
        exercise.type = "Training";
        exercise.image = R.drawable.decline_barbell_bench_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Incline Barbell Bench Press";
        exercise.description = "Lying on an incline bench place your hands up on the bar griping it slightly wider than shoulder width apart. Place your feet on the ground in line with or behind your knees. Create an arch in your lower back and push your chest up. Lift the bar up off the rack so that the bar is above your chest. Lower the bar directly down to the chest with your elbow pointing at approximately 45 degrees. Once the bar touches the chest, pause, and then press directly up again. Continue until all reps are completed and then re-rack the barbell.";
        exercise.type = "Training";
        exercise.image = R.drawable.incline_barbell_bench_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dumbbell Flat Bench Press";
        exercise.description = "Lie on a flat bench holding a dumbbell in each hand with an overhand grip holding the dumbbells straight above your chest. Place your feet on the ground in line with or behind your knees. Create an arch in your lower back and push your chest up. Your palms should be facing forward. Slowly lower the umbbells out and down to chest level with your elbow pointing at a 45 degree angle.As you push the weights up, move your arms in an arc to bring the dumbbells together, until they meet over the centre of your chest.";
        exercise.type = "Training";
        exercise.image = R.drawable.dumbbell_flat_bench_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Decline Dumbbell Bench Press";
        exercise.description = "Lie on a decline bench holding a dumbbell in each hand with an overhand grip holding the dumbbells straight above your chest. Place your under the leg rest. Create an arch in your lower back and push your chest up. Your palms should be facing forward. Slowly lower the dumbbells out and down to chest level with your elbow pointing at a 45 degree angle.As you push the weights up, move your arms in an arc to bring the dumbbells together, until they meet over the centre of your chest.";
        exercise.type = "Training";
        exercise.image = R.drawable.decline_dumbbell_bench_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dips";
        exercise.description = "Standing between the bars of a dip station, grip the bars with your palms facing inwards, keeping your elbows tucked in close to your body. Allow your body weight to hang so it is being supported by your arms and shoulders. Keep your hips straight. Lower your body by slowly bending your elbows and leaning slightly forward and continue down until you feel a stretch in your chest, shoulders and triceps. Your elbow joint should be at approximately 90 degrees at the bottom of the movement.As your strength increases, you can add weight by using a dip belt.";
        exercise.type = "Training";
        exercise.image = R.drawable.dips;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Front Delts, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Machine Chest Press";
        exercise.description = "Sit on the chest press seat with your upper chest just above the horizontal handles. Make sure your feet are flat on the floor. Grasp the handles using a wide overhand grip and place your elbows out to the sides just below your shoulders. Push the levers out until your arms are fully extended. Lower the weight until your hands are at chest level.";
        exercise.type = "Training";
        exercise.image = R.drawable.machine_chest_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Incline Machine Chest Press";
        exercise.description = "Sit on the chest press seat with your upper chest just above the horizontal handles. Make sure your feet are flat on the floor. Grasp the handles using a wide overhand grip and place your elbows out to the sides just below your shoulders. Push the levers out until your arms are fully extended. Lower the weight until your hands are at chest level.";
        exercise.type = "Training";
        exercise.image = R.drawable.incline_machine_chest_press;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest, Shoulders, Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Standing Cable Fly";
        exercise.description = "You will need two cable machines for this exercise. Set the cable setting at shoulder height. Hold the handles with an inverted (palms facing each other) grip. Push the cables out in front of you and take one step forward. Have one foot forward and one foot back. With your arms stretched out in front of your chest bring your arms back and bend your elbows so that you are making a large half circle with your hands. Bring your hands back to chest level then press the cables out again in front of you.";
        exercise.type = "Training";
        exercise.image = R.drawable.standing_cable_fly;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Incline Cable Fly";
        exercise.description = "You will need two cable machines for this exercise plus an incline bench. Set the cable pulley at the bottom setting. Lie on the bench and hold the handles with an inverted (palms facing each other) grip out in front of you. With your arms stretched out in front of your chest bring your arms back and bend your elbows so that you are making a large half circle with your hands. Bring your hands back to chest level then press the cables out again in front of you.";
        exercise.type = "Training";
        exercise.image = R.drawable.incline_cable_fly;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Low Standing Cable Fly\n";
        exercise.description = "You will need two cable machines for this exercise. Set the cable setting to the lowest setting on the cable machine. Hold both handles with an inverted (palms facing each other) grip. Push the cables out in front of you at chest height and take one step forward. Have one foot forward and one foot back. With your arms stretched out in front of your chest bring your arms back and down and bend your elbows so that you are making a large half circle with your hands. Bring your hands back to hip level then press the cables up to chest level again in front of you.";
        exercise.type = "Training";
        exercise.image = R.drawable.low_standing_cable_fly;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "High Cable Fly";
        exercise.description = "You will need two cable machines for this exercise. Set the cable pulley above shoulder height. Grip both handles and push the cable out in front of your. Take one step forward. With your arms stretched out in front of your chest bring your arms back and bend your elbows so that you are making a large half circle with your hands. Bring your hands back to chest level then press the cables out again in front of you.";
        exercise.type = "Training";
        exercise.image = R.drawable.high_cable_fly;
        exercise.main_muscle = "Chest";
        exercise.muscle_groups  = "Chest";
        taskManager.executeExerciseInsertionAsync(db,exercise);


        //   -------------------------------   Back   --------------------------------   //


        exercise = new Exercise();
        exercise.name = "Bent Over Barbell Row";
        exercise.description = "Set up a barbell on the floor or rack and stand facing it with your legs slightly wider than shoulder width apart and your knees slightly bent. Bend forward at your waist and grip the barbell with an underhand grip. Keep your back straight at a 45 degree angle. Keep your head and neck straight. This is the start position. Without moving your torso, exhale and lift the barbell up to the crease in your hip. Keep your elbows close in to your body and squeeze your back muscles.";
        exercise.type = "Training";
        exercise.image = R.drawable.bent_over_barbell_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Rear Delts, Upper Back, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Bent Over Row With Torsonator";
        exercise.description = "Straddle the bar with your knees slightly bent. Bend forward at the waist until your torso is at a 45 degreee angle. Grip the bar close to the weight plates with both hands, using a neutral grip. One hand in front the other. Keeping your back straight and exhaling, pull the bar straight up by bending your elbows until the plates touch your chest. Hold and squeeze your back muscles. Return to the start position in a slow smooth movement to place emphasis on your lats. Inhale as you do so. Keep the bar from touching the floor. Pause then repeat.";
        exercise.type = "Training";
        exercise.image = R.drawable.bent_over_row_with_torsonator;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Standing Cable Row";
        exercise.description = "Adjust a cable pulley to above shoulder level. Attache two cable handles or a V shaped cable attachment handle. In a standing position with a tight core slightly lean back and pull the handle to just below chest level, squeezing your shoulder blade into the middle of your spine as you’re pulling the cable in. Hold the contraction in your back and slowly return the cable to the starting position. Repeat.";
        exercise.type = "Training";
        exercise.image = R.drawable.standing_cable_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dumbbell Row";
        exercise.description = "Lean forward into a bench on one knee. Using a neutral grip, hold a dumbbell in one hand so your palm is facing inward. Your arm should be fully extended and hanging straight down. This is the start position. Pull the dumbbell off the floor towards your hip, flexing your elbow and retracting your shoulder blade. Return to the start position. Control the weight down, don’t let the dumbbell fall down with gravity.";
        exercise.type = "Training";
        exercise.image = R.drawable.dumbbell_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts, Biceps.";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Inverted Row";
        exercise.description = "For this exercise, you can use a Smith machine bar or an Olympic bar on a rack. Set the bar to your waist height. Lie on your back under the bar. Reach up and grip the bar with an underhand grip with both hands. Keeping your body straight, pull yourself up towards the bar by pulling your elbows as far back as you can and trying to touch the bar with your chest. Hold and squeeze your arm and back muscles. Lower yourself back to the start position in a controlled motion. Repeat.";
        exercise.type = "Training";
        exercise.image = R.drawable.inverted_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts, Biceps.";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Bent Over Dumbbell Row";
        exercise.description = "Holding a dumbbell in each hand bend at the waist to approximately a 45 degree angle with the palms of your hands facing each other. Have a slight bend in your knees. Row both dumbbells up to hip level squeezing your shoulder blades together. Return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.bent_over_dumbbell_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Reverse Grip Pull Ups";
        exercise.description = "Standing under a pull up bar, reach up and hold onto the bar with an underhand grip. Make sure your hands are about shoulder width apart. If using a reverse grip, grip the bar at shoulder width. If using an overhand grip, grip the bar wider than shoulder width. Keeping your body straight and not swinging your weight, pull your body up towards the bar by pulling your elbows down towards your chest. Continue lifting until your chest is nearly touching the bar. You should feel a “squeeze” at the base of your lats (about midway down your back and to the side) as they contract. Once your lats have completely contracted at the top of the movement, slowly lower your body to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.reverse_grip_pull_ups;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts and Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Weighted Pull Ups";
        exercise.description = "Standing under a pull up bar, attach a weight chain to your waist with the appropriate weight attached. Reach up and hold onto the bar with an underhand grip. Make sure your hands are about shoulder width apart. If using a reverse grip, grip the bar at shoulder width. If using an overhand grip, grip the bar wider than shoulder width. Keeping your body straight and not swinging your weight, pull your body up towards the bar by pulling your elbows down towards your chest. Continue lifting until your chest is nearly touching the bar. You should feel a “squeeze” at the base of your lats (about midway down your back and to the side) as they contract. Once your lats have completely contracted at the top of the movement, slowly lower your body to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.weighted_pull_ups;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Upper Back, Rear Delts and Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Wide-Grip Lat Pull Down";
        exercise.description = "Using a wide grip bar sit on the seat facing the lat pull down machine. Keep your feet flat and planted firmly on the floor and legs under the leg guard. Using an overhand grip, hold the bar as wider than shoulder width. (Your hands should be about 1 1/2 times body width apart.) Keeping your back straight, pull the bar towards the top of your chest using your shoulders while arcing your elbows into the sides of your body. At the end of the movement squeeze your shoulders together slightly and hold for a count of one. Return to the start position by controlling with bar back up.";
        exercise.type = "Training";
        exercise.image = R.drawable.wide_grip_lat_pull_down;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Middle & Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Straight Arm Pulldown";
        exercise.description = "For this exercise you will need a single cable machine and a straight cable bar or rope. Fix the cable pulley to the top setting. Stand with feet shoulder-width apart. Keep the lower back straight and the core tight. Hold the bar or rope with a wide grip. Keeping your arms straight with a very slight bend in the elbow, pull the bar down to your hips. Don’t grip the bar or rope too hard as this can cause your triceps to take over the movement. Breathe out and squeeze your lats as you do so. Pause and slowly return to the top of the movement. You can also use a more bent over position for extra range of movement.";
        exercise.type = "Training";
        exercise.image = R.drawable.straight_arm_pulldown;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Narrow Grip Seated Row";
        exercise.description = "Sit on the seated row seat with your feet on the foot plates. Reach forward and grasp the V shape handle. With your back straight pull the cable into your mid torso area and squeeze your shoulder blades together and then extend your arms back to the starting position. Don’t sway backwards and forwards.";
        exercise.type = "Training";
        exercise.image = R.drawable.narrow_grip_seated_row;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Middle & Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Neutral Grip Lat Pull Down";
        exercise.description = "Using a narrow and underhand grip, grip the bar shoulder width, sit on the seat facing the lat pull down machine. Keep your feet flat and planted firmly on the floor and legs under the leg guard. Using an underhand grip, hold the bar shoulder width. Keeping your back straight, pull the bar towards the top of your chest using your shoulders while arcing your elbows into the sides of your body. Squeeze your shoulder blades together as you’re pulling the bar down. Return to the start by controlling the bar back up.";
        exercise.type = "Training";
        exercise.image = R.drawable.neutral_grip_lat_pull_down;
        exercise.main_muscle = "Back";
        exercise.muscle_groups  = "Lats, Middle & Upper Back, Rear Delts, Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);


        //   -------------------------------   Legs  --------------------------------   //


        exercise = new Exercise();
        exercise.name = "Barbell Back Squat";
        exercise.description = "Stand with your feet shoulder width apart or wider if required. Start by pushing your hips back and lowering your body by bending at the hips as if sitting down. Continue this movement down until your thighs are below parallel to the floor making sure your knees are pointing in thesame direction as your feet. Hold for a count of one. Push up through your feet while straightening your hips and knees, until you are standing in the start position.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_back_squat;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Quads, Hamstrings and Glutes";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Barbell Front Squat";
        exercise.description = "Start by pushing your hips back and lowering your body by bending at the hips as if sitting down, keeping your chest up and back straight. Continue this movement down until your thighs are slightly lower than parallel to the floor. Make sure your knees are pointing in the same direction as your feet. Hold for a count of one. Push up through your feet while straightening your hips and knees, until you are standing in the start position. Keep your chest up and back straight at all times. Return to the starting position and repeat.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_front_squat;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Glutes, Hamstrings Quadriceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dumbbell Lunges";
        exercise.description = "Holding a dumbbell in each hand stand with your feet shoulder width apart. Step forward with either leg in a long stride. Keep your other foot in place behind you. Bend your knees as you do this so your body is lowered towards the ground. Keep your back straight throughout the movement. Continue down until your back knee is just above the round. (Your front knee be bent at 90 degrees). Hold for a count of one. Push down through your front heel and and you toes of your back foot and straighten both knees to return to the start position. Pause then repeat with your other leg.";
        exercise.type = "Training";
        exercise.image = R.drawable.dumbbell_lunges;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Glutes, Hamstrings & Quadriceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Barbell Lunge";
        exercise.muscle_groups = "Quadriceps, Hamstrings & Glutes";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_lunge;
        exercise.main_muscle = "Legs";
        exercise.description  = "Lift the barbell clear of the rack by pushing with your legs, while straightening your torso. Take two steps backwards away from the rack. Step forward with your right leg and lower your body by bending at both knees. Keep your back straight and be careful to maintain your balance. Aim to lower your body until your front knee angle is approximately 90 degress. Keep your rear heel off the floor and push up through your front foot and the toes of your rear foot and return to the starting position. Complete all the repetitions for one leg before switching.";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dumbbell Deadlifts";
        exercise.muscle_groups = "Hamstrings, Glutes, Quadriceps, Lower and Upper Back";
        exercise.type = "Training";
        exercise.image = R.drawable.dumbbell_deadlifts;
        exercise.main_muscle = "Legs";
        exercise.description  = "Hold a dumbbell in each hand in front of your legs. Place your feet shoulder width apart with your feet pointing straight. Pull your shoulder blades back and down. Brace your core, bend at the hips, pushing your hips back and also bending at the knees. Leaning forwards, run the dumbbells down your legs until you reach just above the floor. Push your weight through your feet as you stand back up straight. As you stand back up your hips and shoulders should rise together and your back should be straight.";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Barbell Deadlift";
        exercise.description = "Stand facing the barbell with your shins as close to the bar as possible. Place your feet shoulder width apart. Your feet can be pointed straight ahead or turned outwards slightly. Squat down, keeping your back straight and grip the bar with a double overhand grip or one hand over and one under at shoulder width. Pull your shoulder blades down. Brace your core and push your weight through your feet as you lift the bar. As you lift the barbell, your hips and shoulders should rise together and your back should be straight. Lower the barbell back to the floor keeping the bar as close to your legs as possible.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_deadlift;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Hamstrings, Glutes, Quadriceps, Lower and Upper Back";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Leg Press";
        exercise.description = "Sit in the leg press machine and place your feet on the platform directly in front of you at shoulder width or wider. Your feet can be pointing straight ahead or slightly outwards. Push through your feet on the plate and straighten your legs until your legs are fully extended. Do not lock your knees out. Take off the safety bar. Slowly lower the platform until your hamstring (back of upper leg) touches your calf (back of lower leg). Push your knees slightly out to the side. Push the plate back up so your legs are fully extended. Return to the starting position by pushing through the heels of your feet, engaging your quadriceps. Lock the safety pin when finished.";
        exercise.type = "Training";
        exercise.image = R.drawable.leg_press;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Glutes, Quadriceps & Hamstrings";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Machine Hack Squat";
        exercise.description = "Place your back flat against the back pad of the hack machine and position your shoulders under the shoulder pads. Place your legs in a shoulder width or wider stance with your toes pointed straight. Grip the side handles of the machine and disengage the safety bar. Straighten your legs, but do not lock your knees. This is the start position. Lower your body down by bending at the knees and driving your knees out. Your range should be until you feel your hamstrings just touch your calves and then return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.machine_hack_squat;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Glutes, Hamstrings & Quadriceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Barbell Hip Thrusters";
        exercise.description = "Sit down with your back against the bench. Hold the barbell across the crease in your hips with your knees bent. Holding the bar in position with both hand and shoulders resting on the bench, thrust your hips in an upwards motion until your hips lock out. Squeezing your glutes on the way up. Return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_hip_thrusters;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Glutes, Hamstrings & Lower Back";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Leg Extensions";
        exercise.description = "Sit on a leg extension machine and place your legs under the pad with your feet pointed forward. The pad should rest just above your ankles. You may need to adjust to suit. Extend your legs until your legs are straight and then return to the starting position. Do not bounce the weight up.";
        exercise.type = "Training";
        exercise.image = R.drawable.leg_extensions;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Quadriceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Hamstring Curl";
        exercise.description = "Lie face down on a leg curl machine and place your ankles under the ankle pad. The pad should rest just above the back of your ankle. Remaining flat on the bench, with no arching of your spine, curl your legs up in a smooth arcing motion by bending your knees until your hamstrings are fully contracted. Slowly lower your legs to the starting position in a smooth arcing motion.";
        exercise.type = "Training";
        exercise.image = R.drawable.hamstring_curl;
        exercise.main_muscle = "Legs";
        exercise.muscle_groups  = "Hamstrings";
        taskManager.executeExerciseInsertionAsync(db,exercise);


        //   -------------------------------   Biceps  --------------------------------   //


        exercise = new Exercise();
        exercise.name = "Barbell Curls";
        exercise.description = "Hold the barbell with an underhand grip shoulder width apart. Pull the barbell up to chest level in an arc formation keeping your entire body straight. Do not swing the weight up or hyper extend your lower back. Slowly return the bar to the starting position. Don’t let the bar drop down.";
        exercise.type = "Training";
        exercise.image = R.drawable.barbell_curls;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "EZ Bar Curls";
        exercise.description = "Hold the EZ Bar with an underhand grip either with a close grip or wider grip. Pull the barbell up to chest level in an arc formation keeping your entire body straight. Do not swing the weight up or hyper extend your lower back. Slowly return the bar to the starting position. Don’t let the bar drop down.";
        exercise.type = "Training";
        exercise.image = R.drawable.ez_bar_curls;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "EZ-Bar Preacher Curl";
        exercise.description = "Set up a preacher curl bench making sure that the seat is set at the right height. The seat shouldn’t be so low that you need to raise your shoulders, or so high that you need to lean over the support pad. Rest your arms on the support pad with your triceps near the top and your elbows midway down the pad. Grip the EZ bar with an underhand grip at shoulder width. Curl the bar in towards your chin and upper chest in a single smooth arc. Hold for a count of one. Lower the bar by extending your arms back to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.ez_bar_preacher_curl;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Dumbbell Preacher Curls";
        exercise.description = "Set up a preacher curl bench making sure that the seat is set at the right height for you. The seat shouldn’t be so low that you need to raise your shoulders, or so high that you need to lean over the support pad. Rest your arm on the support pad with your triceps near the top and your elbow midway down the pad. Grip the dumbbell with an underhand grip at shoulder width. Curl the dumbbell in towards your chin and upper chest in a single smooth arc. Hold for a count of one while squeezing your biceps. Lower the dumbbell by extending your arms back to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.dumbbell_preacher_curls;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Cable Bicep Curls";
        exercise.description = "For this exercise you will need one cable machine with the pulley attachment at the lowest level. You will also need one straight bar or EZ bar attachment. Attach the handle to the cable. Standing with a split stance with your back to the cable machine hold the cable in one hand with your arm extended behind you. Curl the handle up to chest height without bringing your shoulder forward and engaging your shoulders. Return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.cable_bicep_curls;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Seated Incline Dumbbell Curls";
        exercise.description = "Set up an incline bench at 45 degrees. Holding a dumbbell in each hand, lie back on the bench, keeping your shoulders and back firmly against the back rest. Put your arms down by your side with your palms facing in to your body. Curl your arms up, rotating your wrist upwards (palms facing upwards) until the dumbbell is level with your shoulders. Flex or squeeze your bicep at the top of the movement and hold for a count of one. Slowly lower the dumbbells back to the start position, turning your palms back in to your body.";
        exercise.type = "Training";
        exercise.image = R.drawable.seated_incline_dumbbell_curls;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Standing Dumbbell Curl";
        exercise.description = "Holding a dumbbell in each hand, stand with your feet shoulder width apart. Let your arms hang by your side with your palms facing in to the side of your body. Keep your elbows close to your sides. Curl the dumbbells up towards your shoulders, rotating your forearms. Do not swing your hips to get the weight moving. Continue raising the dumbbells until they are level with your shoulders with your palms facing your. Your forearm should be in a vertical position. Squeeze or flex your bicep and hold for a count of one. Slowly lower the dumbbells to the starting position. Repeat.";
        exercise.type = "Training";
        exercise.image = R.drawable.standing_dumbbell_curl;
        exercise.main_muscle = "Biceps";
        exercise.muscle_groups  = "Biceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);


        //   -------------------------------   Triceps  --------------------------------   //


        exercise = new Exercise();
        exercise.name = "Cable Rope Overhead Triceps Extension";
        exercise.description = "Attach a rope to the top pulley of a cable station. Grip the rope with both hands using a neutral grip (palms facing inwards). Turn your body away from the cable station. Take a split stance and lean forward. Fully extend your arms straight out over the top of your head pointing straight ahead. Keep your elbows close to your head. When your elbows are fully extended, hold for a count of one while squeezing your triceps. Return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.cable_rope_overhead_triceps_extension;
        exercise.main_muscle = "Triceps";
        exercise.muscle_groups  = "Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Lying Barbell Skull Crushers";
        exercise.description = "Set up a flat bench. Sit on the bench and position your feet on the floor with your feet behind your knees. Lie on the bench with an arch in your lower back holding the bar above your chest with your arms extended and elbows locked. Lower the bar downward towards your forehead in a smooth arc. Continue lowering the bar until it is just above your forehead and press the bar back over your chest. You can also use an EZ bar for this exercise.";
        exercise.type = "Training";
        exercise.image = R.drawable.lying_barbell_skull_crushers;
        exercise.main_muscle = "Triceps";
        exercise.muscle_groups  = "Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Lying EZ Bar Triceps Extension";
        exercise.description = "Set up a flat bench. Sit on the bench and position your feet on the floor with your feet behind your knees. Lie on the bench with an arch in your lower back holding the bar above your chest with your arms extended and elbows locked. Lower the EZ bar downward towards the back of your head in a smooth arc. Continue lowering the bar until it is at the height of the back of your head. Push the bar back above your chest.";
        exercise.type = "Training";
        exercise.image = R.drawable.lying_ez_bar_triceps_extension;
        exercise.main_muscle = "Triceps";
        exercise.muscle_groups  = "Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Cable Rope Pushdown";
        exercise.description = "Set up a cable machine with a rope attachment attached to the top pulley. Grip the rope with an inverted grip, with your hands close together. Position your feet shoulder width apart, with knees slightly bent for stability. Push rope down and pull the rope apart to the side so that your hands finish by your hips at the bottom of the movement.";
        exercise.type = "Training";
        exercise.image = R.drawable.cable_rope_pushdown;
        exercise.main_muscle = "Triceps";
        exercise.muscle_groups  = "Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);

        exercise = new Exercise();
        exercise.name = "Single Arm Cable Pushdown";
        exercise.description = "Set up a cable machine with a handle attached to the top pulley. Grip the handle with an underhand grip, with your hand at shoulder height, palm facing towards you. Position your feet shoulder width apart, with knees slightly bent for stability. Pull the handle down and fully extend your elbow until your arm is straight. Return to the starting position.";
        exercise.type = "Training";
        exercise.image = R.drawable.single_arm_cable_pushdown;
        exercise.main_muscle = "Triceps";
        exercise.muscle_groups  = "Triceps";
        taskManager.executeExerciseInsertionAsync(db,exercise);
    }
}
