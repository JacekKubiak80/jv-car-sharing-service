package com.example.demo.service;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.repository.PaymentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDto> getPaymentsForUser(Long userId) {
        return paymentRepository.findByRentalUserId(userId).stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }
}
