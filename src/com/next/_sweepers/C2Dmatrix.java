package com.next._sweepers;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

/**
 * Created by dorinsuletea on 7/21/16.
 */

public class C2Dmatrix {
    private S2DMatrix m_Matrix;

    class S2DMatrix {
        double _11, _12, _13;
        double _21, _22, _23;
        double _31, _32, _33;

        public S2DMatrix() {
            _11 = 0;
            _12 = 0;
            _13 = 0;
            _21 = 0;
            _22 = 0;
            _23 = 0;
            _31 = 0;
            _32 = 0;
            _33 = 0;
        }

    }

    public C2Dmatrix(){
        Identity();
    }

    public void Identity(){
        m_Matrix._11 = 1; m_Matrix._12 = 0; m_Matrix._13 = 0;

        m_Matrix._21 = 0; m_Matrix._22 = 1; m_Matrix._23 = 0;

        m_Matrix._31 = 0; m_Matrix._32 = 0; m_Matrix._33 = 1;
    }

    public void Translate(double x, double y){
        S2DMatrix mat = new S2DMatrix();

        mat._11 = 1; mat._12 = 0; mat._13 = 0;

        mat._21 = 0; mat._22 = 1; mat._23 = 0;

        mat._31 = x;    mat._32 = y;    mat._33 = 1;

        S2DMatrixMultiply(mat);
    }

    //create a scale matrix
    public void	Scale(double xScale, double yScale){
        S2DMatrix mat = new S2DMatrix();

        mat._11 = xScale; mat._12 = 0; mat._13 = 0;

        mat._21 = 0; mat._22 = yScale; mat._23 = 0;

        mat._31 = 0; mat._32 = 0; mat._33 = 1;
    }

    public void Rotate(double rot){
        S2DMatrix mat = new S2DMatrix();

        double Sin = Math.sin(rot);
        double Cos = Math.cos(rot);

        mat._11 = Cos;  mat._12 = Sin; mat._13 = 0;

        mat._21 = -Sin; mat._22 = Cos; mat._23 = 0;

        mat._31 = 0; mat._32 = 0;mat._33 = 1;

        //and multiply
        S2DMatrixMultiply(mat);
    }

    public void TransformSPoints(List<Point.Double> points){

    }

    public void S2DMatrixMultiply(S2DMatrix mIn)
    {
        S2DMatrix mat_temp = new S2DMatrix();

        //first row
        mat_temp._11 = (m_Matrix._11*mIn._11) + (m_Matrix._12*mIn._21) + (m_Matrix._13*mIn._31);
        mat_temp._12 = (m_Matrix._11*mIn._12) + (m_Matrix._12*mIn._22) + (m_Matrix._13*mIn._32);
        mat_temp._13 = (m_Matrix._11*mIn._13) + (m_Matrix._12*mIn._23) + (m_Matrix._13*mIn._33);

        //second
        mat_temp._21 = (m_Matrix._21*mIn._11) + (m_Matrix._22*mIn._21) + (m_Matrix._23*mIn._31);
        mat_temp._22 = (m_Matrix._21*mIn._12) + (m_Matrix._22*mIn._22) + (m_Matrix._23*mIn._32);
        mat_temp._23 = (m_Matrix._21*mIn._13) + (m_Matrix._22*mIn._23) + (m_Matrix._23*mIn._33);

        //third
        mat_temp._31 = (m_Matrix._31*mIn._11) + (m_Matrix._32*mIn._21) + (m_Matrix._33*mIn._31);
        mat_temp._32 = (m_Matrix._31*mIn._12) + (m_Matrix._32*mIn._22) + (m_Matrix._33*mIn._32);
        mat_temp._33 = (m_Matrix._31*mIn._13) + (m_Matrix._32*mIn._23) + (m_Matrix._33*mIn._33);

        m_Matrix = mat_temp;
    }

    public void TransformSPoints(ArrayList<Point.Double> vPoint)
    {
        for (int i=0; i<vPoint.size(); ++i)
        {
            double tempX =(m_Matrix._11*vPoint.get(i).x) + (m_Matrix._21*vPoint.get(i).y) + (m_Matrix._31);

            double tempY = (m_Matrix._12*vPoint.get(i).x) + (m_Matrix._22*vPoint.get(i).y) + (m_Matrix._32);

            Point.Double pt = new Point.Double(tempX,tempY);
            vPoint.set(i,pt);
        }
    }
}
