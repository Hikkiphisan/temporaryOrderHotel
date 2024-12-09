package org.example.temporary.dao;

import org.example.temporary.model.RoomBooking;

import java.sql.Timestamp;
import java.util.List;


import java.sql.SQLException;

public interface RoomBookingDAO {

    List<RoomBooking> selectAllRooms() throws SQLException;


    List<RoomBooking> selectAllBooking() throws SQLException;

    RoomBooking selectRoomById(int roomId) throws SQLException;

    public RoomBooking selectBookingById(int roomId) throws SQLException;


    RoomBooking selectRoomByCode(String roomCode) throws SQLException;


    boolean insertBooking(RoomBooking roomBooking, int userId, Timestamp bookingStartDate, Timestamp bookingEndDate, double bookingTotalPrice) throws SQLException;


    boolean insertRoom(RoomBooking room) throws SQLException;


    boolean updateRoomtoSQL(RoomBooking room) throws SQLException;


    boolean updateRoomStatustoSql_DatPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) throws SQLException;
    boolean updateRoomStatustoSql_HuyPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) throws SQLException;

    boolean updateRoomStatustoSql_HuyPhong_rutgon(int bookingId) throws SQLException;


    //thu nghiem
    public boolean updateRoomStatustoSql(String roomCode, String status) throws SQLException;

    //thu nghiem
    public boolean insertBookingToSql(String roomCode, String customerName, String startTime, String endTime) throws SQLException;


    boolean deleteRoomtoSQL(int roomId) throws SQLException;








    public boolean update_to_table_monthly_revenue_on_Sql_DatPhong(double total_revenue, Timestamp startTime, Timestamp endTime) throws SQLException;




    public double[] calculateHoursByMonth(Timestamp startTime, Timestamp endTime) ;



}

