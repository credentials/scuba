/*
 * $Id: $
 */

package net.sourceforge.scuba.swing;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sourceforge.scuba.util.Hex;

/**
 * File system tree viewer for ISO7816-4 file system
 * (specifically GSM 11.11 SIM file system).
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class FileSystemTree extends JTree
{
    private static final long serialVersionUID = -1891706552932007492L;
    
    private Collection<FilePathSelectionListener> filePathSelectionListeners;

    /**
     * Creates a tree.
     * 
     * @param fileSystem a list of paths
     * 
     * @throws IllegalArgumentException if paths to not all start in a single root file
     */
    public FileSystemTree(short[][] fileSystem) {
        super(createNodes(fileSystem));
        filePathSelectionListeners = new ArrayList<FilePathSelectionListener>();
        addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                Object[] path = e.getPath().getPath();
                short[] fidPath = new short[path.length];
                for (int i = 0; i < fidPath.length; i++) {
                    fidPath[i] = fromUserObject(((DefaultMutableTreeNode)path[i]).getUserObject());
                }
                notifyFilePathSelectionListeners(fidPath);
            }
        }); 
    }

    private static DefaultMutableTreeNode createNodes(short[][] fileSystem) {
        DefaultMutableTreeNode root = null;
        for (short[] path: fileSystem) {
            if (root == null) {
                root = addNodes(null, path, 0);
            } else if (fromUserObject(root.getUserObject()) == path[0]) {
                addNodes(root, path, 1);
            } else {
                throw new IllegalArgumentException("File system is not a tree"); 
            }
        }
        return root;
    }

    private static DefaultMutableTreeNode addNodes(DefaultMutableTreeNode parent, short[] path, int index) {
        if (index >= path.length) {
            return parent;
        } else if (parent == null) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(toUserObject(path[index]));
            addNodes(node, path, index + 1);
            return node;
        } else {
            DefaultMutableTreeNode node = null;
            if (parent.getChildCount() != 0) { 
                node = (DefaultMutableTreeNode)parent.getFirstChild();
                while (node != null && path[index] != fromUserObject(node.getUserObject())) {
                    node = node.getNextSibling();
                }
            }
            if (node == null) {
                node = new DefaultMutableTreeNode(toUserObject(path[index]));
                parent.add(node);
            }
            addNodes(node, path, index + 1);
            return parent;
        }
    }

    private static short fromUserObject(Object userObject) {
        return (short)Hex.hexStringToShort((String)userObject);
    }

    private static String toUserObject(short fid) {
        return Integer.toHexString(fid & 0xFFFF).toUpperCase();
    }
    
    public void addFilePathSelectionListener(FilePathSelectionListener l) {
        filePathSelectionListeners.add(l);
    }
    
    private void notifyFilePathSelectionListeners(short[] fidPath) {
        for (FilePathSelectionListener l: filePathSelectionListeners) { l.pathSelected(fidPath); }
    }                       
    
    public interface FilePathSelectionListener
    {
        void pathSelected(short[] path);
    }
}
