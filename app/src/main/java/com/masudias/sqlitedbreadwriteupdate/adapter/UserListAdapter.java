package com.masudias.sqlitedbreadwriteupdate.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.masudias.sqlitedbreadwriteupdate.R;
import com.masudias.sqlitedbreadwriteupdate.database.DBConstants;
import com.masudias.sqlitedbreadwriteupdate.util.TimeUtils;

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Cursor cursor;

    public UpdateUserActionListener updateUserListener;
    public DeleteUserActionListener deleteUserListener;

    public UserListAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ViewHolder vh = (ViewHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null || cursor.isClosed()) return 0;
        else return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;

        private final TextView userNameTextView;
        private final TextView createdAtTextView;
        private final ImageView deleteUserButton;
        private final ImageView updateUserButton;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            createdAtTextView = (TextView) itemView.findViewById(R.id.created_at);
            deleteUserButton = (ImageView) itemView.findViewById(R.id.delete_button);
            updateUserButton = (ImageView) itemView.findViewById(R.id.update_button);
        }

        public void bindView(int pos) {

            cursor.moveToPosition(pos);

            final int userId = cursor.getInt(cursor.getColumnIndex(DBConstants.KEY_ID));
            final String userName = cursor.getString(cursor.getColumnIndex(DBConstants.KEY_USER_NAME));
            final Long createdAt = cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_CREATED_AT));

            userNameTextView.setText(userName);
            createdAtTextView.setText(TimeUtils.formatDateWithTime(createdAt));

            deleteUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteUserListener.onDeleteUserActionReceived(userId);
                }
            });

            updateUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUserListener.onUpdateUserActionReceived(userId);
                }
            });
        }
    }
}