package org.example.temporary.service;

import org.example.temporary.model.RoomBooking;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface RoomBookingService {


    List<RoomBooking> getAllRooms();


    RoomBooking getRoomById(int roomId);


    RoomBooking getRoomByCode(String roomCode);

    boolean addRoom(RoomBooking room);


    boolean updateRoom(RoomBooking room);


    //phuong thuc thu nghiem
    boolean updateRoom(String roomTypeId, String customerName, String startTime, String endTime);

    boolean updateRoom_DatPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime);

     boolean updateRoom_HuyPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime);

     boolean updateRoom_HuyPhong_rutgon(int bookingId);

    boolean deleteRoom(int roomId);


    public boolean updateTableRevenue_DatPhong(double total_revenue, Timestamp startTime, Timestamp endTime);
}

