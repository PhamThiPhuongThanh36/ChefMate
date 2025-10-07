# 👩‍🍳 ChefMate

Ứng dụng được viết bằng **Kotlin** với **Jetpack Compose** và **Room**.  
ChefMate cho phép **lưu trữ công thức nấu ăn, lập kế hoạch nguyên liệu và quản lý danh sách mua sắm** một cách tiện lợi.  

---

## Tính năng chính
- **Quản lý công thức nấu ăn**
  - Thêm công thức mới.  
  - Chỉnh sửa công thức có sẵn.  
  - Xóa công thức.  

- **Quản lý nguyên liệu mua sắm**
  - Thêm nguyên liệu thủ công (người dùng nhập trực tiếp).  
  - Thêm nguyên liệu tự động từ công thức nấu ăn đã chọn.  
  - Tự động gộp và xử lý các nguyên liệu **trùng lặp**.  

- **Quản lý danh sách mua sắm**
  - Tạo danh sách mua sắm.  
  - Thêm, xóa, chỉnh sửa nguyên liệu trong danh sách.  
  - Đánh dấu nguyên liệu đã mua / chưa mua.  

- **Lưu trữ dữ liệu với Room**
  - Dữ liệu được lưu local, không mất khi thoát app.  

- **UI hiện đại với Jetpack Compose**
  - Giao diện theo phong cách declarative, tự động cập nhật khi dữ liệu thay đổi.  

---

## 📱 Giao diện

### Màn hình chính và Trang cá nhân
<p align="center">
  <img src="https://github.com/user-attachments/assets/748b7ac0-dc0c-44a3-af3e-f57ccd5d0e43" alt="Screen 1" width="250" />
  <img src="https://github.com/user-attachments/assets/acbae993-c39b-406c-8897-d68a72bee6cf" alt="Screen 5" width="250" />  
</p>

### Thêm Công thức

<p align="center">
  <img src="https://github.com/user-attachments/assets/4e3917a2-4c69-41a5-9764-d89216eb5e48" alt="Screen 2" width="190" />
  <img src="https://github.com/user-attachments/assets/53519ab8-762a-4cd1-8dfa-5c903a907bab" alt="Screen 3" width="190" />
  <img src="https://github.com/user-attachments/assets/a551fd27-af24-44ff-903d-336677a0e0a9" alt="Screen 6" width="190" />
  <img src="https://github.com/user-attachments/assets/2385846c-733f-4467-a484-f33323816ed5" alt="Screen 7" width="190" />
</p>

### Lập danh sách mua sắm

<p align="center">
  <img src="https://github.com/user-attachments/assets/bac616ee-e8ee-4da8-8493-e437077e37b3" alt="Screen 8" width="250" />
  <img src="https://github.com/user-attachments/assets/110880a3-7ec2-49fe-9e11-3255b57d159e" alt="Screen 9" width="250" />
</p>

---

## 🛠 Công nghệ sử dụng
- [Kotlin] – ngôn ngữ chính.  
- [Jetpack Compose] – UI toolkit.  
- [Room] – ORM cho database.  
- [ViewModel] + [Flow] – quản lý state & dữ liệu.  
- [Navigation Compose] – điều hướng giữa các màn hình.  

---

## 📂 Cấu trúc dữ liệu (Room Entities)
```kotlin
@Entity(tableName = "Recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int? = null,
    val userId: Int? = null,
    val recipeName: String,
    val image: String,
    val cookingTime: String,
    val ration: Int,
    val viewCount: Int,
    val likeQuantity: Int,
    val isPublic: Boolean,
    val createdAt: String,
)

@Entity(tableName = "Ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int? = null,
    val recipeId: Int? = null,
    val ingredientName: String,
    val weight: String,
    val unit: String
)


@Entity(tableName = "Steps")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val stepId: Int? = null,
    val recipeId: Int,
    val description: String
)


@Entity(tableName = "Tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Int? = null,
    val recipeId: Int,
    val tagName: String
)

@Entity(tableName = "ShoppingLists")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    val shoppingId: Int? = null,
    val status: Boolean,
    val createdAt: String,
)

@Entity(tableName = "ShoppingItems")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val siId: Int? = null,
    val shoppingId: Int? = null,
    val siName: String,
    val siWeight: String,
    val siUnit: String,
    val status: Boolean
)
