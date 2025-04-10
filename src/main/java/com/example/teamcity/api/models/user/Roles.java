package com.example.teamcity.api.models.user;

import com.example.teamcity.api.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles extends BaseModel {
    private List<Role> role;
}
