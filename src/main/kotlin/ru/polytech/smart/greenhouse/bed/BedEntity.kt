package ru.polytech.smart.greenhouse.bed

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.crop.CropEntity
import ru.polytech.smart.greenhouse.device.DeviceEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "beds")
data class BedEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "greenhouse_id")
    var greenhouse: GreenhouseEntity,

    var name: String,

    @ManyToOne
    @JoinColumn(name = "crops_id")
    var crop: CropEntity?,

    @OneToMany(mappedBy = "bed", fetch = FetchType.LAZY)
    var devices: MutableList<DeviceEntity> = mutableListOf(),

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)