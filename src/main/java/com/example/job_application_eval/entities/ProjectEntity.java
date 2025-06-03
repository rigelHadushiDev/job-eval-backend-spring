package com.example.job_application_eval.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(nullable = false, name = "project_title" )
    private String projectTitle;


    @Column(nullable = false, name = "description",columnDefinition = "TEXT" )
    private String description;

    @Column(nullable = false, name= "link")
    private String link;

    @Column(nullable = false, name= "finished")
    private Boolean finished = true;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(nullable = false, name= "technologies_or_tools")
    private String technologiesOrTools;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
    
}
