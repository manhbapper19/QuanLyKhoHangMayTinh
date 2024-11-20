package controller;

import bean.DanhMucBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.Dto.UserDetail;
import org.w3c.dom.Node;
import view.MainFrm;
import view.NhaCungCapFrm;
import view.NhapHang;
import view.PhieuNhap;
import view.PhieuXuat;
import view.SanPhamFrm;
import view.TaiKhoanFrm;
import view.ThongKe;
import view.TonKho;
import view.XuatHang;
import view.UpdateThongTin;

/**
 *
 * @author ASUS
 */
public class ChuyenManHinhController {
    private UserDetail userDetail;
    private JPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> listItem = null;

    public ChuyenManHinhController(JPanel jpnRoot, UserDetail userDetail) {
        this.root = jpnRoot;
        this.userDetail = userDetail;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(96, 100, 191));
        jlbItem.setBackground(new Color(96, 100, 191));
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new SanPhamFrm());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }

    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "SanPham":
                    node = new SanPhamFrm();
                    break;
                case "NhaCungCap":
                    node = new NhaCungCapFrm();
                    break;
                case "NhapHang":
                    node = new NhapHang(userDetail.getUserName(), userDetail.getId());
                    break;
                case "PhieuNhap":
                    node = new PhieuNhap();
                    break;
                case "XuatHang":
                    node = new XuatHang(userDetail.getUserName(), userDetail.getId());
                    break;
                case "PhieuXuat":
                    node = new PhieuXuat();
                    break;
                case "TonKho":
                    node = new TonKho();
                    break;
                case "TaiKhoan":
                    node = new TaiKhoanFrm();
                    break;
                case "ThongKe":
                    node = new ThongKe();
                    break;
                case "DoiThongTin":
                    Dialog updateDialog = new UpdateThongTin((JFrame) SwingUtilities.getWindowAncestor(root), true, userDetail.getUserName(), userDetail.getId(),userDetail.getEmail());
                    updateDialog.setVisible(true);
                    return;
                case "DangXuat" :
                   int confirm = JOptionPane.showConfirmDialog(root, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(root);
                        mainFrame.dispose();
                        mainFrame = new MainFrm();
                        mainFrame.setVisible(true);
                    }
                    return;
                default:
                    node = new SanPhamFrm();
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackgroud(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(96, 100, 191));
            jlbItem.setBackground(new Color(96, 100, 191));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(96, 100, 191));
            jlbItem.setBackground(new Color(96, 100, 191));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(76, 175, 80));
                jlbItem.setBackground(new Color(76, 175, 80));
            }
        }

    }

    private void setChangeBackgroud(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(96, 100, 191));
                item.getJlb().setBackground(new Color(96, 100, 191));
            } else {
                item.getJpn().setBackground(new Color(76, 175, 80));
                item.getJlb().setBackground(new Color(76, 175, 80));
            }
        }
    }
}
