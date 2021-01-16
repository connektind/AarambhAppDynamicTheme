package com.example.aarambhappdynamictheme.sheardPrefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class AarambhSharedPreference {
    public static final String PREFS_NAME = "LAFARM_PREFS";
    public static final String GUEST = "Guest";
    public static final String CLASS_ID="ClassId";
    public static final String STUDENT_NAME="StudentName";
    public static final String STUDENT_ID="StudentId";
    public static final String STUDENT_MOBILE ="StudentMobile";
    public static final String STUDENT_PROFILE="StudentProfile";

    public static final String SUBJECT_NAME="Subject_name";
    public static final String TOPIC_NAME="topic_name";
    public static final String USER_TOKEN="user_token";
    public static final String COURSE_ID="CourseId";
    public static final String TOPIC_ID="TopicId";
    public static final String PARENT_ID="ParentId";
    public static final String PRATICES_ID="Pratices_ID";
    public static final String SCHOOL_ID="School_ID";
    public static  final String CLASS_NAME="Class_Name";

    public static void saveStudentProfileToPreference(Context context,String studentProfile){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(STUDENT_PROFILE,studentProfile);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadStudentProfileFromPreference(Context ctx){
        String studentProfile="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            studentProfile = pref.getString(STUDENT_PROFILE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentProfile;
    }
    public static void saveClassNameToPreference(Context context, String Class_Name){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(CLASS_NAME,Class_Name);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadClassNameFromPreference(Context ctx){
        String Class_Name="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            Class_Name = pref.getString(CLASS_NAME,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Class_Name;
    }


    public static void saveParentIDToPreference(Context context, String Parent_Id){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(PARENT_ID,Parent_Id);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadParentIdFromPreference(Context ctx){
        String ParentId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            ParentId = pref.getString(PARENT_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ParentId;
    }

    public static void saveTopicIdToPreference(Context context, String TopicId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(TOPIC_ID,TopicId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadTopicIdFromPreference(Context ctx){
        String topicId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            topicId = pref.getString(TOPIC_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return topicId;
    }
    public static void saveStudentIdToPreference(Context context, String StudentId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(STUDENT_ID,StudentId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadStudentIdFromPreference(Context ctx){
        String studentId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            studentId = pref.getString(STUDENT_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentId;
    }
    public static void saveStudentMobileToPreference(Context context, String StudentMobile){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(STUDENT_MOBILE,StudentMobile);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadStudentMobileFromPreference(Context ctx){
        String studentMobile="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            studentMobile = pref.getString(STUDENT_MOBILE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentMobile;
    }



    public static void saveCourseIdToPreference(Context context, String CourseId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(COURSE_ID,CourseId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadCourseIdFromPreference(Context ctx){
        String courseId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            courseId = pref.getString(COURSE_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return courseId;
    }


    public static void saveStudentNameToPreference(Context context, String studentName){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(STUDENT_NAME,studentName);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadStudentNameFromPreference(Context ctx){
        String studentName="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            studentName = pref.getString(STUDENT_NAME,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentName;
    }
    public static void saveUserTokenToPreference(Context context, String userToken){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(USER_TOKEN,userToken);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadUserTokenFromPreference(Context ctx){
        String userToken="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            userToken = pref.getString(USER_TOKEN,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return userToken;
    }



    public static void saveClassIdToPreference(Context context, String classId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(CLASS_ID,classId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadClassIdFromPreference(Context ctx){
        String classId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            classId = pref.getString(CLASS_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return classId;
    }

    public static void saveSubjectNameToPreference(Context context, String subject_name){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(SUBJECT_NAME,subject_name);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadSubjectNameFromPreference(Context ctx){
        String subject_name="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            subject_name = pref.getString(SUBJECT_NAME,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject_name;
    }

    public static void saveTopicNameToPreference(Context context, String topic_name){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(TOPIC_NAME,topic_name);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadTopicNameFromPreference(Context ctx){
        String topic_name="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            topic_name = pref.getString(TOPIC_NAME,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return topic_name;
    }

    public static void saveSchoolIdToPreference(Context context, String schoolId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(SCHOOL_ID, schoolId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadSchoolIdFromPreference(Context ctx){
        String schoolId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            schoolId = pref.getString(SCHOOL_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return schoolId;
    }

}
