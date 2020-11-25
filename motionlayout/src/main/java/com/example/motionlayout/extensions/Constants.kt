package com.example.motionlayout.extensions

private val layoutConstraints = arrayListOf(
    "startToStart",
    "startToEnd",
    "endToStart",
    "endToEnd",
    "topToTop",
    "topToBottom",
    "bottomToTop",
    "bottomToBottom"
)

private val chainConstraints = arrayListOf(
    "horizontalChainStyle",
    "verticalChainStyle"
)

private val referenceIdsConstraints = arrayListOf(
    "mReferenceIds"
)

private val biasConstraints = arrayListOf(
    "horizontalBias",
    "verticalBias"
)

private val barrierConstraints = arrayListOf(
    "mBarrierDirection",
    "mBarrierMargin",
    "mBarrierAllowsGoneWidgets"
)

private val circularConstraints = arrayListOf(
    "circleConstraint",
    "circleAngle",
    "circleRadius"
)

enum class ConstraintType {
    LAYOUT,
    BIAS,
    CHAIN,
    REFERENCE_IDS,
    BARRIER,
    CIRCULAR
}

val attrListMap = mapOf(
    ConstraintType.LAYOUT to layoutConstraints,
    ConstraintType.BIAS to biasConstraints,
    ConstraintType.CHAIN to chainConstraints,
    ConstraintType.REFERENCE_IDS to referenceIdsConstraints,
    ConstraintType.BARRIER to barrierConstraints,
    ConstraintType.CIRCULAR to circularConstraints
)