package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Collaboration;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;

public class CollaborationRowMapper implements RowMapper<Collaboration> {
    Collaboration collaboration = new Collaboration();

    @Override
    public int[] getRowsForPaths(TreePath[] treePaths) {
        return new int[0];
    }
}
