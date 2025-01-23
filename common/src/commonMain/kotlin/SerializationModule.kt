import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val inputTypeModule = SerializersModule {
    polymorphic(InputType::class) {
        subclass(InputType.MOUSE::class)
        subclass(InputType.KEYBOARD::class)
    }
}
val loopTypeModule = SerializersModule {
    polymorphic(LoopType::class) {
        subclass(LoopType.SINGLE::class)
        subclass(LoopType.INFINITE::class)
        subclass(LoopType.CUSTOM::class)
    }
}
val timeUnitModule = SerializersModule {
    polymorphic(TimeUnit::class) {
        subclass(TimeUnit.Millisecond::class)
        subclass(TimeUnit.Seconds::class)
        subclass(TimeUnit.Minutes::class)
    }
}