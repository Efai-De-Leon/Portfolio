using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RandomCoordinatesGenerator
{
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public RandomCoordinatesGenerator()
    {
        this.minX = -35f;
        this.maxX = -2f;
        this.minY = 3f;
        this.maxY = 23f;
    }

    public Vector3 GetRandomPosition()
    {
        float x = Random.Range(minX, maxX);
        float y = Random.Range(minY, maxY);
        return new Vector3(x, y, 0f);
    }
}
