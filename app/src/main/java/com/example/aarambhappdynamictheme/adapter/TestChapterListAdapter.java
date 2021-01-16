package com.example.aarambhappdynamictheme.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.TestAcivity;
import com.example.aarambhappdynamictheme.model.SubjChapterTopicListModel;
import com.example.aarambhappdynamictheme.model.SubjectChapterListModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;

import java.util.ArrayList;

public class TestChapterListAdapter extends RecyclerView.Adapter<TestChapterListAdapter.MyViewHolder> {

    Context context;
    ArrayList<SubjectChapterListModel> subjectChapterListModelArrayList;
    String class_id;
    ArrayList<String> sixth_Chapter;
    ArrayList<String> topicName;
    YouTubeTitle chap;
    ArrayList<VideoDetails> videoDetailsArrayList;
    ArrayList<YouTubePlayList> youTubePlayListArrayList;


    public TestChapterListAdapter(Context context, ArrayList<String> sixth_Chapter, ArrayList<YouTubePlayList> youTubePlayListArrayList) {
        this.context = context;
        this.sixth_Chapter = sixth_Chapter;
        this.youTubePlayListArrayList = youTubePlayListArrayList;
        class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
        chap = new YouTubeTitle();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_test_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.e("chapter_nameTEST", sixth_Chapter.get(position));
        final String chapter = sixth_Chapter.get(position);
//        try {
//            if (chapter.contains("Nutrition in Animals")) {
//                sixth_Chapter.remove("Nutrition in Animals");
//            }
//        }catch (Exception e){e.printStackTrace();}
        holder.chapter_testname.setText(chapter);
        holder.take_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("classIdnnnnnn", class_id);
                switch (class_id) {
                    case "1":
                        Log.e("6thPosition", position + "");
                        String getChapter = sixth_Chapter.get(position);
                        String playListId = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter);
                        Log.e("Adapter_PlayListId", playListId);
                        chap.setTopic(topicName);
                        AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
                        Intent intent = new Intent(context, TestAcivity.class);
                        //if (holder.chapter_testname.equals(getChapter)) {
                            intent.putExtra("ChapterNameTest",getChapter);
                        //}

                        ((Activity) context).startActivityForResult(intent, 201);
                        break;
                    case "2":
                        try {
                            Log.e("7thPosition", position + "");
                            String getChapter1 = sixth_Chapter.get(position);
                            if(getChapter1.contains("Nutrition in animals")){
                                Toast.makeText(context, "No Test Available", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String playListId1 = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter1);
                                Log.e("Adapter_PlayListId", playListId1);
                                chap.setTopic(topicName);
                                AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
                                Intent intent1 = new Intent(context, TestAcivity.class);
                                //intent1.putExtra("7th_Details", playListId1);
                                intent1.putExtra("7thChapterNameTest", getChapter1);
                                ((Activity) context).startActivityForResult(intent1, 202);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "3":
                        try {
                            Log.e("8thPosition", position + "");
                            String getChapter2 = sixth_Chapter.get(position);
                            //VideoDetails video = videoDetailsArrayList.get(position);
                            //ArrayList<String> topicName = getTopicNamesBySubject(getChapter,videoDetailsArrayList);
                            String playListId2 = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter2);
                            Log.e("Adapter_PlayListId", playListId2);
                            chap.setTopic(topicName);
                            AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
                            Intent intent2 = new Intent(context, TestAcivity.class);
                            //intent2.putExtra("8th_Details", playListId2);
                            intent2.putExtra("8thChapterNameTest",getChapter2);
                            ((Activity) context).startActivityForResult(intent2, 203);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
//                    default:
//                        String getChapter3 = sixth_Chapter.get(position);
//                        VideoDetails video3 = videoDetailsArrayList.get(position);
//                        ArrayList<String> topicName3 = getTopicNamesBySubject(getChapter3,videoDetailsArrayList);
//                        chap.setTopic(topicName3);
//                        Intent intent3 = new Intent(context, RamaThemeTopicVideoActivity.class);
//                        intent3.putExtra("7th_Details", chap);
//                        intent3.putExtra("VDAR",video3);
//                        context.startActivity(intent3);
//                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return subjectChapterListModelArrayList.size();
        return sixth_Chapter.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chapter_testname, take_test;
        ArrayList<SubjChapterTopicListModel> subjChapterTopicListModelArrayList;

        ArrayList<VideoDetails> videoDetailsArrayList;
        TextColorGradient textColorGradient = new TextColorGradient();
        String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=25&key=AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo";
         String base_color_one,base_color_two;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_testname = itemView.findViewById(R.id.chapter_testname);
            subjChapterTopicListModelArrayList = new ArrayList<>();
            videoDetailsArrayList = new ArrayList<>();
            take_test = itemView.findViewById(R.id.take_test);
            base_color_one= AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(context);
            base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(context);
            textColorGradient.getColorTextGradient(take_test, base_color_one, base_color_two);
        }
    }

    public String getPlayListIdAccordingtoChapter(ArrayList<YouTubePlayList> playLists, String chapterName) {
        try {
            String className = "", playListId = "";
            for (int x = 0; x < playLists.size(); x++) {
                YouTubePlayList vd = playLists.get(x);
                Log.e("X:- ", vd.getPlayListTitle());
                String video_title = vd.getPlayListTitle();
                String title_arr[] = video_title.split("_");
                Log.e("Title Arr:- ", title_arr.length + "");
                className = title_arr[0];
                Log.e("className", className);
                Log.e("Chapterin PL Title", vd.getPlayListTitle());
                String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
                if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1")) {
                    String subject = title_arr[1].replace(" ", "");
                    String chapter = title_arr[2];
                    String name = title_arr[3];
                    String title = chapter + " " + name;//Chapter 2 Component of Food

                    if (title.equalsIgnoreCase(chapterName)) {
                        playListId = vd.getPlayListId();
                        Log.e("PL_ID", playListId);
                    }

                } else if (className.equalsIgnoreCase("7th") && class_id.equalsIgnoreCase("2")) {
                    String subject = title_arr[1].replace(" ", "");
                    String chapter = title_arr[2];
                    String name = title_arr[3];
                    String title = chapter + " "+name ;//Chapter 2 Component of Food

                    if (title.equalsIgnoreCase(chapterName)) {
                        playListId = vd.getPlayListId();
                        Log.e("PL_ID", playListId);
                    }

                } else if (className.equalsIgnoreCase("8th") && class_id.equalsIgnoreCase("3")) {
                    String subject = title_arr[1].replace(" ", "");
                    String chapter = title_arr[2];
                    String name = title_arr[3];
                    String title = chapter + " " + name;//Chapter 2 Component of Food

                    if (title.equalsIgnoreCase(chapterName)) {
                        playListId = vd.getPlayListId();
                        Log.e("PL_ID", playListId);
                    }

                }

            }
            return playListId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
