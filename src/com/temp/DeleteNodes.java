package com.temp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class DeleteNodes extends JFrame {

  protected JTree tree;

  public static void main(String[] args) {
    DeleteNodes dn = new DeleteNodes(new JTree());
    dn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    dn.setSize(400, 300);
    dn.setVisible(true);
  }

  public DeleteNodes(JTree jt) {
    super();
    tree = jt;
    getContentPane().add(tree);
    tree.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event) {
        if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
            && (tree.getSelectionCount() > 0)) {
          showMenu(event.getX(), event.getY());
        }
      }
    });
  }

  protected void showMenu(int x, int y) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem mi = new JMenuItem("Delete");
    TreePath path = tree.getSelectionPath();
    Object node = path.getLastPathComponent();
    if (node == tree.getModel().getRoot()) {
      mi.setEnabled(false);
    }
    popup.add(mi);
    mi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        deleteSelectedItems();
      }
    });
    popup.show(tree, x, y);
  }

  protected void deleteSelectedItems() {
    DefaultMutableTreeNode node;
    DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
    TreePath[] paths = tree.getSelectionPaths();
    for (int i = 0; i < paths.length; i++) {
      node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
      model.removeNodeFromParent(node);
    }
  }
}
