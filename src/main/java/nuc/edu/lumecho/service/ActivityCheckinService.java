package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.checkin.CheckInRequest;

public interface ActivityCheckinService {
    String checkIn(CheckInRequest request);
}