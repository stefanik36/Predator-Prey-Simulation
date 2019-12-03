package com.agh.abm.pps.gui.gesture

import com.agh.abm.pps.gui.layout.PannableCanvas
import com.agh.abm.pps.gui.layout.ZoomCanvas
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent

class SceneGestures(canvas: ZoomCanvas) {

    private val sceneDragContext = DragContext()


    // right mouse button => panning
    val onMousePressedEventHandler: EventHandler<MouseEvent> =
        EventHandler { event ->
            if (!event.isSecondaryButtonDown)
                return@EventHandler

            sceneDragContext.mouseAnchorX = event.sceneX
            sceneDragContext.mouseAnchorY = event.sceneY

            sceneDragContext.translateAnchorX = canvas.tX
            sceneDragContext.translateAnchorY = canvas.tY
        }

    // right mouse button => panning
    val onMouseDraggedEventHandler: EventHandler<MouseEvent> =
        EventHandler { event ->
            if (!event.isSecondaryButtonDown)
                return@EventHandler

            canvas.tX = sceneDragContext.translateAnchorX + event.sceneX - sceneDragContext.mouseAnchorX
            canvas.tY = sceneDragContext.translateAnchorY + event.sceneY - sceneDragContext.mouseAnchorY
            canvas.requestUpdate()
            event.consume()
        }

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    // currently we only use Y, same value is used for X
    // note: pivot value must be untransformed, i. e. without scaling
    val onScrollEventHandler: EventHandler<ScrollEvent> = EventHandler {
        val delta = 1.2
        val change = 50.0

        if (it.deltaY < 0) {
            canvas.zoom /= delta
            canvas.tX += change
            canvas.tY += change
        }
        else {
            canvas.zoom *= delta
            canvas.tY -= change
            canvas.tX -= change
        }
        canvas.requestUpdate()
        it.consume()
    }
//
//    companion object {
//
//        private const val MAX_SCALE = 10.0
//        private const val MIN_SCALE = .1
//
//
//        fun clamp(value: Double, min: Double, max: Double): Double {
//
//            if (value.compareTo(min) < 0)
//                return min
//
//            return if (value.compareTo(max) > 0) max else value
//
//        }
//    }
}