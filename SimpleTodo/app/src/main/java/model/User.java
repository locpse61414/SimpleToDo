package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;


/**
 * Created by phant on 17-Jan-17.
 */

public class User extends SugarRecord implements Parcelable {
    private String taskname;
    private String taskNote;
    private String priority;
    private String status;
    private String dueDate;

    public User() {
    }

    public User(String taskname) {
        this.taskname = taskname;
    }

    protected User(Parcel in) {
        taskname = in.readString();
        taskNote = in.readString();
        priority = in.readString();
        status = in.readString();
        dueDate = in.readString();
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getTaskname() {
        return taskname;
    }

    public User(String taskname, String taskNote, String priority, String status, String dueDate) {
        this.taskname = taskname;
        this.taskNote = taskNote;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskname);
        dest.writeString(taskNote);
        dest.writeString(priority);
        dest.writeString(status);
        dest.writeString(dueDate);
    }
}
