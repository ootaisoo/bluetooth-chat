package ru.worksolutions.bluetothchatdemo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {

    private static final String LOG_TAG = DeviceListAdapter.class.getName();

    private List<BluetoothDevice> devices;
    private DeviceSelectedListener listener;
    private Context context;

    public DeviceListAdapter(List<BluetoothDevice> devices, DeviceSelectedListener listener, Context context) {
        this.devices = devices;
        this.listener = listener;
        this.context = context;
    }

    public interface DeviceSelectedListener {
        void onDeviceSelected(BluetoothDevice device);
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_holder, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        BluetoothDevice device = devices.get(position);
        holder.device = devices.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceMACAdress.setText(device.getAddress());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        BluetoothDevice device;
        private TextView deviceName;
        private TextView deviceMACAdress;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            deviceMACAdress = itemView.findViewById(R.id.device_mac_adress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeviceSelected(device);
                }
            });
        }
    }
}
