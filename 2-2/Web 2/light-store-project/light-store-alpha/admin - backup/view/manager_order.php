<div class="page-wrapper">
    <?php 
include_once "./model/Connection.php";
$connect = new Connection();
        $instance_page = 1;
        $limit = 20;
        
        if(isset($_GET['page_num'])){
            $instance_page = $_GET['page_num'];
        }
        $start = ($instance_page-1)*$limit;
        /*
        SELECT hoadon.mahd, ngaydat, giaohang.ngaygh,hinhthuc_chitra, SUM(dongia), SUM(soluong), giaohang.tinhtrang
        FROM hoadon,giaohang,ct_dh,khachhang,bongden
        WHERE ct_dh.mabd = bongden.mabd AND hoadon.mahd = ct_dh.mahd AND hoadon.magh = giaohang.magh AND hoadon.mahd = ct_dh.mahd AND hoadon.makh = khachhang.makh AND ct_dh.mabd=bongden.mabd
        GROUP BY hoadon.mahd
        */
        //Khoi tao query
        $query = "SELECT hoadon.mahd, ngaydat, giaohang.ngaygh,hinhthuc_chitra, SUM(dongia), SUM(soluong), giaohang.tinhtrang
        FROM ((((hoadon INNER JOIN khachhang ON hoadon.makh = khachhang.makh)
                INNER JOIN giaohang ON hoadon.magh = giaohang.magh)
                INNER JOIN ct_dh ON  hoadon.mahd = ct_dh.mahd)
                INNER JOIN bongden ON ct_dh.mabd=bongden.mabd) ";
        $need_get = "";
        $condition = "WHERE 1";
        if(isset($_GET['order_state'])){
            $condition .= " AND giaohang.tinhtrang='".$_GET['order_state']."' ";
            $need_get .= "&order_state=".$_GET['order_state'];
        }
        //Query khi co tim kiem
        if(isset($_POST['search'])){
            if($_POST['customer_name']!=""){
                //SELECT * from hoadon,khachhang where hoadon.makh = khachhang.makh AND khachhang.tenkh LIKE '%anh%' 
                $condition .= " AND khachhang.tenkh LIKE '%".$_POST['customer_name']."%'  ";
                $need_get .= "&customer_name=".$_POST['customer_name'];
            }
            if($_POST['product_name']!=""){
                $condition .= " AND bongden.tenbd LIKE '%".$_POST['product_name']."%' ";
                $need_get .= "&product_name=".$_POST['product_name'];
            }
            if($_POST['category_id']!=""){
                $condition .= " AND bongden.maloai='".$_POST['category_id']."' ";
                $need_get .= "&category_id=".$_POST['category_id'];
            }
            if($_POST['payment']!=""){
                $condition .= " AND hinhthuc_chitra='".$_POST['payment']."' ";
                $need_get .= "&payment=".$_POST['payment'];
            }
            if($_POST['order_date']!=""){
                $condition .= " AND ngaydat>='".$_POST['order_date']."' ";
                $need_get .= "&order_date=".$_POST['order_date'];
            }
            if($_POST['comp_date']!=""){
                $condition .= " AND ngaygh<='".$_POST['comp_date']."' ";
                $need_get .= "&comp_date=".$_POST['comp_date'];
            }
        }
            //Tao need get khi co tim kiem && phan trang
    if(isset($_GET['page_num'])){
        if(isset($_GET['customer_name'])){
            $condition .= " AND khachhang.tenkh LIKE '%".$_GET['customer_name']."%'  ";
            $need_get .= "&customer_name_id=".$_GET['customer_name'];
        }
        if(isset($_GET['product_name'])){
            $condition .= " AND bongden.tenbd LIKE '%".$_GET['product_name']."%' ";
            $need_get .= "&product_name=".$_GET['product_name'];
        }
        if(isset($_GET['category_id']) ){
            $condition .= " AND bongden.maloai='".$_GET['category_id']."' ";
            $need_get .= "&category_id=".$_GET['category_id'];
        }
        if(isset($_GET['payment'])){
            $condition .= " AND hinhthuc_chitra='".$_GET['payment']."' ";
                $need_get .= "&payment=".$_GET['payment'];
        }
        if(isset($_GET['order_date'])){
            $condition .= " AND ngaydat>='".$_GET['order_date']."' ";
                $need_get .= "&order_date=".$_GET['order_date'];
        } 
        if(isset($_GET['comp_date'])){
            $condition .= " AND ngaygh<='".$_GET['comp_date']."' ";
                $need_get .= "&comp_date=".$_GET['comp_date'];
        } 
    }
        $query .= $condition;
        $total_record = $connect->totalRecord("SELECT hoadon.mahd FROM
        ((((hoadon INNER JOIN khachhang ON hoadon.makh = khachhang.makh)
        INNER JOIN giaohang ON hoadon.magh = giaohang.magh)
        INNER JOIN ct_dh ON  hoadon.mahd = ct_dh.mahd)
        INNER JOIN bongden ON ct_dh.mabd=bongden.mabd)"
        .$condition);
        $total_page = ceil($total_record/$limit);
        //Gioi han record
        $query .= " GROUP BY hoadon.mahd LIMIT $start,$limit ";
        echo $query;
         $order_stmt = $connect->select($query);
?>
    <div class="page-breadcrumb">
        <div class="row">
            <div class="col-12 d-flex no-block align-items-center">
                <h4 class="page-title">Qu???n l?? ????n h??ng</h4>
                <div class="ml-auto text-right">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="?page=home">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Qu???n L?? ????n H??ng</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="?page=manager_order"> T???t c??? </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?page=manager_order&order_state=0"> ??ang x??? l?? </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?page=manager_order&order_state=1"> ??ang giao h??ng </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?page=manager_order&order_state=2"> ????n h??ng th??nh c??ng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?page=manager_order&order_state=-1"> ????n h??ng th???t b???i</a>
                </li>
            </ul>
        </div>
        <div class="row">
            <form action="" method="POST">
            <input type="text" name="customer_name" placeholder="Kh??ch h??ng">
            <input type="text" name="product_name" placeholder="S???n ph???m">
            <label for="category_id"> Lo???i s???n ph???m </label>
            <select name="category_id" id="" >
                <option value=""></option>
                <?php 
                $list_category = $connect->select("SELECT maloai,tenloai FROM loaibd");
                while($row = $list_category->fetch())
                {
                    echo "<option value='".$row['maloai']."' >". $row['tenloai'] ."</option>";
                }
                ?>
            </select>
            <label for="payment"> Thanh to??n </label>
            <select name="payment" id="">
                <option value=""></option>
                <?php 
                $list_payment = $connect->select("SELECT hinhthuc_chitra FROM hoadon GROUP BY hinhthuc_chitra");
                while($row = $list_payment->fetch())
                {
                    echo "<option value='".$row['hinhthuc_chitra']."' >". $row['hinhthuc_chitra'] ."</option>";
                }
                ?>
            </select>
            <label for="order_date"> Ng??y ?????t </label>
            <input type="date" name="order_date" >
            <label for="comp_date"> Ng??y ho??n th??nh </label>
            <input type="date" name="comp_date" >
            <input type="submit" name="search" value="T??m ki???m">
            </form>
        </div>
        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <td> <span> S??? ????n h??ng </span></td>
                        <td> <span> Ng??y ?????t </span></td>
                        <td> <span> Ng??y ho??n th??nh </span></td>
                        <td> <span> H??nh th???c thanh to??n </span></td>
                        <td> <span> Gi?? tr??? ????n h??ng </span></td>
                        <td> <span> S??? s???n ph???m </span></td>
                        <td> <span> T??nh tr???ng </span></td>
                        <td> <span> Thao t??c </span></td>
                    </tr>
                </thead>
                <tbody>
                    <?php
                        while($row = $order_stmt->fetch()){
                            switch ($row["tinhtrang"]) {
                                case "0":
                                    $change_state = "<li><span><a href=\"./controller/manager_order_perform.php?order_id=".$row['mahd']."&new_state=1\"> Giao h??ng </a></span></li>";
                                    break;
                                case "1":
                                    $change_state = "
                                    <li>
                                    <span><a href=\"./controller/manager_order_perform.php?order_id=".$row['mahd']."&new_state=2\"> ???? giao </a></span>
                                    </li>
                                    <li>
                                    <span><a href=\"./controller/manager_order_perform.php?order_id=".$row['mahd']."&new_state=-1\"> Giao h??ng th???t b???i </a></span>
                                    </li>";
                                    break;
                                default:
                                    $change_state ="";
                                    break;
                            }

                            echo
                            "<tr>
                                <td> <span>". $row["mahd"] ."</span></td>
                                <td> <span>". $row["ngaydat"] ."</span></td>
                                <td> <span>". $row["ngaygh"] ."</span></td>
                                <td> <span>". $row["hinhthuc_chitra"] ."</span></td>
                                <td> <span>". $row["SUM(dongia)"] ."</span></td>
                                <td> <span>". $row["SUM(soluong)"] ."</span></td>
                                <td> <span>". $row["tinhtrang"] ."</span></td>
                                <td> 
                                <ul>
                                    <li>
                                        <span><a href=\"?page=order_detail&order_id=".$row['mahd']."\"> Chi ti???t </a></span>
                                    </li>
                                    ".$change_state."
                                </ul>
                                </td>
                            </tr>";
                        }
                    ?>
                </tbody>
            </table>
        </div>
        <div class="row">
            <ul class="pagination justify-content-center" style="margin:20px 0; width: 100%;">
                <?php 

                    for($i = 1; $i<=$total_page; $i++){
                        if($instance_page==$i){
                            echo "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">$i</a></li>";
                            continue;
                        }
                        echo "<li class=\"page-item\"><a class=\"page-link\" href=\"?page=manager_order&page_num=$i".$need_get."\">$i</a></li>";
                    }
                ?>
            </ul>
        </div>
    </div>

</div>