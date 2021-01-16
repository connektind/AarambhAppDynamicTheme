package com.example.aarambhappdynamictheme.sheardPrefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class AarambhThemeSharedPrefreence {
    public static final String PREFS_NAME = "AARAMBH_THEME";
    public static final String GUEST = "Guest";
    public static final String BASE_IMAGE="BaseImage";
    public static final String BASE_IMAGE_TRANSPARENT="BaseImageTransparent";
    public static final String BASE_COLOR_ONE="BaseColorOne";
    public static final String BASE_COLOR_TWO="BaseColorTwo";
    public static final String NOTIFICATION_IMAGE="NotificationImage";
    public static final String BACK_ARROW_ICON="BackArrowIcon";
    public static final String BOOKMARK_IMAGE="BookMarkImage";
    public static final String CAMERA_ICON="CameraIcon";
    public static final String CANCLE_ICON="CancleIcon";
    public static final String EMAIL_ICON="EmailIcon";
    public static final String Mobile_ICON="MobileIcon";
    public static final String USER_ICON="UserIcon";
    public static final String GENDER_ICON="GenderIcon";
    public static final String DOB_ICON="DOBIcon";
    public static final String ADDRESS_ICON="AddressIcon";
    public static final String NOTIFICATION_ICON="NotificationIcon";
    public static final String LOGOUT_ICON="LogOutIcon";
    public static final String MENU_NAVIGATION_ICON="MenuNavigationIcon";
    public static final String THANKU_BACKGROUND="ThankuBackground";
    public static final String LIVE_ARROW="LiveArrow";
    public static final String SCHOOL_LOGO="SchoolLogo";
    public static String loadLiveArrowFromPreference(Context ctx){
        String LiveArrow="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            LiveArrow = pref.getString(LIVE_ARROW,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return LiveArrow;
    }
    public static void saveLiveArrowPreference(Context context, String LiveArrow){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(LIVE_ARROW,LiveArrow);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String loadSchoolLogoFromPreference(Context ctx){
        String SchoolLogo="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            SchoolLogo = pref.getString(SCHOOL_LOGO,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return SchoolLogo;
    }
    public static void saveSchoolLogoPreference(Context context, String SchoolLogo){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(SCHOOL_LOGO,SchoolLogo);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveBaseImageToPreference(Context context, String BaseImage){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BASE_IMAGE,BaseImage);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBaseImageFromPreference(Context ctx){
        String BaseImage="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BaseImage = pref.getString(BASE_IMAGE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BaseImage;
    }

    public static void saveBaseImageTransparentToPreference(Context context, String BaseImageTransparent){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BASE_IMAGE_TRANSPARENT,BaseImageTransparent);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBaseImageTransparentFromPreference(Context ctx){
        String BaseImageTransparent="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BaseImageTransparent = pref.getString(BASE_IMAGE_TRANSPARENT,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BaseImageTransparent;
    }
    public static void saveBaseColorOnePreference(Context context, String BaseColorOne){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BASE_COLOR_ONE,BaseColorOne);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBaseColorOneFromPreference(Context ctx){
        String BaseColorOne="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BaseColorOne = pref.getString(BASE_COLOR_ONE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BaseColorOne;
    }
    public static void saveBaseColorTwoPreference(Context context, String BaseColorTwo){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BASE_COLOR_TWO,BaseColorTwo);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBaseColorTwoFromPreference(Context ctx){
        String BaseColorTwo="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BaseColorTwo = pref.getString(BASE_COLOR_TWO,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BaseColorTwo;
    }
    public static void saveNotificationImageToPreference(Context context, String NotificationIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(NOTIFICATION_IMAGE,NotificationIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadNotificationImageFromPreference(Context ctx){
        String NotificationIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            NotificationIcon = pref.getString(NOTIFICATION_IMAGE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return NotificationIcon;
    }
    public static void saveBackArrowIconToPreference(Context context, String BackArrowIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BACK_ARROW_ICON,BackArrowIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBackArrowIconFromPreference(Context ctx){
        String BackArrowIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BackArrowIcon = pref.getString(BACK_ARROW_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BackArrowIcon;
    }
    public static void saveBookMarkImageToPreference(Context context, String BookMarkImage){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(BOOKMARK_IMAGE,BookMarkImage);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadBookMarkImageFromPreference(Context ctx){
        String BookMarkImage="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            BookMarkImage = pref.getString(BOOKMARK_IMAGE,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return BookMarkImage;
    }
    public static void saveCameraIconToPreference(Context context, String CameraIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(CAMERA_ICON,CameraIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadCameraIconFromPreference(Context ctx){
        String CameraIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            CameraIcon = pref.getString(CAMERA_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return CameraIcon;
    }
    public static void saveCancleIconToPreference(Context context, String CancleIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(CANCLE_ICON,CancleIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadCancleIconFromPreference(Context ctx){
        String CancleIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            CancleIcon = pref.getString(CANCLE_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return CancleIcon;
    }
    public static void saveEmailIconToPreference(Context context, String EmailIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(EMAIL_ICON,EmailIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadEmailIconFromPreference(Context ctx){
        String EmailIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            EmailIcon = pref.getString(EMAIL_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmailIcon;
    }
    public static void saveMobileIconToPreference(Context context, String MobileIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(Mobile_ICON,MobileIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadMobileIconFromPreference(Context ctx){
        String MobileIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            MobileIcon = pref.getString(Mobile_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return MobileIcon;
    }
    public static void saveUserIconToPreference(Context context, String UserIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(USER_ICON,UserIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadUserIconFromPreference(Context ctx){
        String UserIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            UserIcon = pref.getString(USER_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return UserIcon;
    }
    public static void saveGenderIconToPreference(Context context, String GenderIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(GENDER_ICON,GenderIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadGenderIconFromPreference(Context ctx){
        String GenderIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            GenderIcon = pref.getString(GENDER_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return GenderIcon;
    }
    public static void saveDOBIconToPreference(Context context, String DOBIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(DOB_ICON,DOBIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadDOBIconFromPreference(Context ctx){
        String DOBIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            DOBIcon = pref.getString(DOB_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return DOBIcon;
    }
    public static void saveAddressIconToPreference(Context context, String AddressIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(ADDRESS_ICON,AddressIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadAddressIconFromPreference(Context ctx){
        String AddressIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            AddressIcon = pref.getString(ADDRESS_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AddressIcon;
    }
    public static void saveNotificationIconToPreference(Context context, String NotificationIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(NOTIFICATION_ICON,NotificationIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadNotificationIconFromPreference(Context ctx){
        String NotificationIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            NotificationIcon = pref.getString(NOTIFICATION_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return NotificationIcon;
    }
    public static void saveLogOutIconToPreference(Context context, String LogOutIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(LOGOUT_ICON,LogOutIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadLogOutIconFromPreference(Context ctx){
        String LogOutIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            LogOutIcon = pref.getString(LOGOUT_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return LogOutIcon;
    }
    public static void saveMenuNavigationIconToPreference(Context context, String MenuNavigationIcon){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(MENU_NAVIGATION_ICON,MenuNavigationIcon);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadMenuNavigationIconFromPreference(Context ctx){
        String MenuNavigationIcon="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            MenuNavigationIcon = pref.getString(MENU_NAVIGATION_ICON,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return MenuNavigationIcon;
    }
    public static void saveThankuBackgroundToPreference(Context context, String ThankuBackground){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
            editor.putString(THANKU_BACKGROUND,ThankuBackground);
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadThankuBackgroundFromPreference(Context ctx){
        String ThankuBackground="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            ThankuBackground = pref.getString(THANKU_BACKGROUND,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ThankuBackground;
    }


}
