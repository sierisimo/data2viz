package io.data2viz.geo.projection

import io.data2viz.geo.GeoBounds
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class GeoBoundsTests : TestBase() {

    @Test
    fun geobounds_feature_LEGACY() {
        val result = GeoBounds().result(Feature(MultiPoint(arrayOf(pt(-123.0, 39.0), pt(-122.0, 38.0)))))
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_featureCollection_LEGACY() {
        val result = GeoBounds().result(
            FeatureCollection(
                arrayOf(
                    Feature(Point(pt(-123.0, 39.0))),
                    Feature(Point(pt(-122.0, 38.0)))
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_GeometryCollection_LEGACY() {
        val result = GeoBounds().result(
            GeometryCollection(
                arrayOf(
                    Point(pt(-123.0, 39.0)),
                    Point(pt(-122.0, 38.0))
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_simple_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_symmetry_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(-30.0, -20.0),
                    pt(130.0, 40.0)
                )
            )
        )
        val result2 = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(130.0, 40.0),
                    pt(-30.0, -20.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(result2.x0, result2.y0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(result2.x1, result2.y1)
    }

    @Test
    fun geobounds_LineString_containing_coincident_points_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_meridian_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(.0, 60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(.0, 60.0)
    }

    @Test
    fun geobounds_LineString_equator_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(1.0, .0),
                    pt(60.0, .0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(60.0, .0)
    }

    @Test
    fun geobounds_LineString_containing_an_inflection_point_in_the_northern_hemisphere_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(-45.0, 60.0),
                    pt(45.0, 60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-45.0, 60.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(45.0, 67.792345)
    }

    @Test
    fun geobounds_LineString_containing_an_inflection_point_in_the_southern_hemisphere_LEGACY() {
        val result = GeoBounds().result(
            LineString(
                arrayOf(
                    pt(-45.0, -60.0),
                    pt(45.0, -60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-45.0, -67.792345)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(45.0, -60.0)
    }

    @Test
    fun geobounds_MultiLineString_LEGACY() {
        val result = GeoBounds().result(
            MultiLineString(
                arrayOf(
                    arrayOf(
                        pt(-123.0, 39.0),
                        pt(-122.0, 38.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_simple_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_antimeridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(179.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_antimeridian_2_points_near_primary_meridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0),
                    pt(-1.0, .0),
                    pt(1.0, .0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_primary_meridian_2_points_near_antimeridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-1.0, .0),
                    pt(1.0, .0),
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_4_mixed_points_near_primary_meridian_and_antimeridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-1.0, .0),
                    pt(-179.0, 39.0),
                    pt(1.0, .0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_3_points_near_antimeridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(178.0, 38.0),
                    pt(179.0, 39.0),
                    pt(-179.0, 37.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(178.0, 37.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_various_points_near_antimeridian_LEGACY() {
        val result = GeoBounds().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(-179.0, 38.0),
                    pt(178.0, 39.0),
                    pt(-178.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(178.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-178.0, 39.0)
    }

    @Test
    fun geobounds_MultiPolygon_LEGACY() {
        val result = GeoBounds().result(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(-123.0, 39.0),
                            pt(-122.0, 39.0),
                            pt(-122.0, 38.0),
                            pt(-123.0, 39.0)
                        )
                    ),
                    arrayOf(
                        arrayOf(
                            pt(10.0, 20.0),
                            pt(20.0, 20.0),
                            pt(20.0, 10.0),
                            pt(10.0, 10.0),
                            pt(10.0, 20.0)
                        )
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 10.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(20.0, 39.001067)
    }

    @Test
    fun geobounds_Point_LEGACY() {
        val result = GeoBounds().result(
            Point(
                pt(-123.0, 39.0)
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 39.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-123.0, 39.0)
    }

    @Test
    fun geobounds_Polygon_simple_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-123.0, 39.0),
                        pt(-122.0, 39.0),
                        pt(-122.0, 38.0),
                        pt(-123.0, 39.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.001067)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_small_counter_clockwise_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(10.0, .0),
                        pt(10.0, 10.0),
                        pt(.0, 10.0),
                        pt(.0, .0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(180.0, 90.0)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_large_lat_lon_rectangle_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-170.0, 80.0),
                        pt(.0, 80.0),
                        pt(170.0, 80.0),
                        pt(170.0, -80.0),
                        pt(.0, -80.0),
                        pt(-170.0, -80.0),
                        pt(-170.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-170.0, -89.119552)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(170.0, 89.119552)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_south_pole_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(10.0, 80.0),
                        pt(170.0, 80.0),
                        pt(-170.0, 80.0),
                        pt(-10.0, 80.0),
                        pt(10.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(180.0, 88.246216)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_excluding_both_poles_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(10.0, 80.0),
                        pt(170.0, 80.0),
                        pt(-170.0, 80.0),
                        pt(-10.0, 80.0),
                        pt(-10.0, .0),
                        pt(-10.0, -80.0),
                        pt(-170.0, -80.0),
                        pt(170.0, -80.0),
                        pt(10.0, -80.0),
                        pt(10.0, .0),
                        pt(10.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(10.0, -88.246216)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-10.0, 88.246216)
    }

    @Test
    fun geobounds_Polygon_south_pole_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-60.0, -80.0),
                        pt(60.0, -80.0),
                        pt(180.0, -80.0),
                        pt(-60.0, -80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(180.0, -80.0)
    }

    @Test
    fun geobounds_Polygon_ring_LEGACY() {
        val result = GeoBounds().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-60.0, -80.0),
                        pt(60.0, -80.0),
                        pt(180.0, -80.0),
                        pt(-60.0, -80.0)
                    ),
                    arrayOf(
                        pt(-60.0, -89.0),
                        pt(180.0, -89.0),
                        pt(60.0, -89.0),
                        pt(-60.0, -89.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-180.0, -89.499961)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(180.0, -80.0)
    }

    @Test
    fun geobounds_nestedCollection_LEGACY() {
        val result = GeoBounds().result(
            FeatureCollection(
                arrayOf(
                    Feature(
                        GeometryCollection(
                            arrayOf(
                                Point(
                                    pt(-120.0, 47.0)
                                ),
                                Point(
                                    pt(-119.0, 46.0)
                                )
                            )
                        )
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(-120.0, 46.0)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(-119.0, 47.0)
    }

    /*@Test
    fun geobounds_null_geometries_LEGACY() {
        val result = GeoBounds().result(Feature(null))

        doubleArrayOf(result.x0, result.y0) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
        doubleArrayOf(result.x1, result.y1) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }*/
}