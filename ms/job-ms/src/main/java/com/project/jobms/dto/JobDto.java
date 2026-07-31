package com.project.jobms.dto;

public record JobDto(Long id,
String title,
String description,
String minSalary,
String maxSalary,
String location) {
}
