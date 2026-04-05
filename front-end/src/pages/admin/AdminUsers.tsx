import React, { useState, useEffect } from 'react';
import axiosClient from '../../api/axiosClient';
import toast from 'react-hot-toast';
import { ShieldAlert, Trash2, Plus, Edit, Users as UsersIcon, ShoppingBag, ShieldCheck } from 'lucide-react';

export const AdminUsers = () => {
  const [activeTab, setActiveTab] = useState<'all' | 'admins' | 'shoppers'>('all');
  const [users, setUsers] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  // Modals state
  const [showAdminModal, setShowAdminModal] = useState(false);
  const [showSellerModal, setShowSellerModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showSellerEditModal, setShowSellerEditModal] = useState(false);
  const [showShopperEditModal, setShowShopperEditModal] = useState(false);

  // Forms state
  const [formData, setFormData] = useState({ username: '', email: '', password: '', companyName: '', firstName: '', lastName: '' });
  const [selectedUserId, setSelectedUserId] = useState<string | number | null>(null);

  useEffect(() => {
    fetchUsers();
  }, [activeTab]);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      let endpoint = '/api/admin/users';
      if (activeTab === 'admins') endpoint = '/api/admin/admins';
      else if (activeTab === 'shoppers') endpoint = '/api/admin/shoppers';

      const response = await axiosClient.get(endpoint);
      
      const payload = response.data;
      if (Array.isArray(payload)) {
        setUsers(payload);
      } else if (payload && typeof payload === 'object' && 'content' in payload) {
         setUsers(payload.content);
      } else {
        setUsers(payload ? [payload] : []);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setFormData({ username: '', email: '', password: '', companyName: '', firstName: '', lastName: '' });
  };

  const handleDelete = async (id: string | number) => {
    if (!window.confirm('Bu kullanıcıyı silmek istediğinize emin misiniz?')) return;
    try {
      await axiosClient.delete(`/api/admin/users/${id}`);
      toast.success('Kullanıcı silindi.');
      fetchUsers();
    } catch (error) {
      console.error(error);
    }
  };

  const handleCreateAdmin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axiosClient.post('/api/admin/admins', { username: formData.username, email: formData.email, password: formData.password });
      toast.success('Admin oluşturuldu!');
      setShowAdminModal(false);
      resetForm();
      fetchUsers();
    } catch (error) { console.error(error); }
  };

  const handleCreateSeller = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axiosClient.post('/api/admin/sellers', { username: formData.username, email: formData.email, password: formData.password, companyName: formData.companyName });
      toast.success('Satıcı oluşturuldu!');
      setShowSellerModal(false);
      resetForm();
      fetchUsers();
    } catch (error) { console.error(error); }
  };

  const handleEditUser = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axiosClient.put(`/api/admin/users/${selectedUserId}`, { username: formData.username, email: formData.email, password: formData.password });
      toast.success('Kullanıcı güncellendi!');
      setShowEditModal(false);
      resetForm();
      fetchUsers();
    } catch (error) { console.error(error); }
  };

  const handleEditSeller = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axiosClient.put(`/api/admin/sellers/${selectedUserId}`, { 
         username: formData.username, 
         email: formData.email, 
         companyName: formData.companyName 
      });
      toast.success('Satıcı özel bilgileri güncellendi!');
      setShowSellerEditModal(false);
      resetForm();
      fetchUsers();
    } catch (error) { console.error(error); }
  };

  const handleEditShopperDetails = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axiosClient.put(`/api/admin/shoppers/${selectedUserId}/details`, { 
         firstName: formData.firstName, 
         lastName: formData.lastName
      });
      toast.success('Müşteri (Shopper) bilgileri güncellendi!');
      setShowShopperEditModal(false);
      resetForm();
      fetchUsers();
    } catch (error) { console.error(error); }
  };

  const openEditModal = (user: any) => {
    setSelectedUserId(user.id);
    setFormData({ username: user.username, email: user.email || '', password: '', companyName: '', firstName: '', lastName: '' });
    setShowEditModal(true);
  };

  const openSellerEditModal = async (sellerId: string | number) => {
    setSelectedUserId(sellerId);
    setFormData({ username: '', email: '', password: '', companyName: '', firstName: '', lastName: '' });
    setShowSellerEditModal(true);
    
    try {
       const response = await axiosClient.get(`/api/admin/sellers/${sellerId}`);
       const sellerData = response.data;
       setFormData({
          username: sellerData.username || '',
          email: sellerData.email || '',
          password: '',
          companyName: sellerData.companyName || '',
          firstName: '',
          lastName: ''
       });
    } catch(err) {
       console.log(err);
       toast.error('Satıcı detayı yüklenemedi!');
    }
  };

  const openShopperEditModal = async (shopperId: string | number) => {
    setSelectedUserId(shopperId);
    setFormData({ username: '', email: '', password: '', companyName: '', firstName: '', lastName: '' });
    setShowShopperEditModal(true);

    try {
       const response = await axiosClient.get(`/api/admin/shoppers/${shopperId}`);
       const shopperData = response.data; 
       setFormData({
          username: '',
          email: '',
          password: '',
          companyName: '',
          firstName: shopperData.firstName || '',
          lastName: shopperData.lastName || ''
       });
    } catch(err) {
       console.log(err);
       toast.error('Müşteri detayı yüklenemedi!');
    }
  };

  const openCreateAdmin = () => { resetForm(); setShowAdminModal(true); };
  const openCreateSeller = () => { resetForm(); setShowSellerModal(true); };

  return (
    <div className="animate-in fade-in">
      <div className="flex flex-col xl:flex-row justify-between items-start xl:items-center mb-6 gap-4">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Sistem Kullanıcıları</h2>
          <p className="text-gray-500 text-sm mt-1">Platformdaki üyeleri rol bazlı izole olarak denetleyin.</p>
        </div>
        <div className="flex gap-2 flex-wrap">
           <button onClick={openCreateSeller} className="flex items-center gap-2 bg-orange-100 hover:bg-orange-200 text-orange-700 px-4 py-2 rounded-lg font-medium transition-colors">
             <Plus className="w-5 h-5" /> Satıcı Ekle
           </button>
           <button onClick={openCreateAdmin} className="flex items-center gap-2 bg-red-100 hover:bg-red-200 text-red-700 px-4 py-2 rounded-lg font-medium transition-colors">
             <ShieldAlert className="w-5 h-5" /> Admin Ekle
           </button>
        </div>
      </div>

      <div className="flex space-x-1 bg-gray-100 p-1 rounded-xl mb-6 w-full lg:w-max">
        <button onClick={() => setActiveTab('all')} className={`flex items-center gap-2 px-5 py-2.5 rounded-lg font-medium text-sm transition-all ${activeTab === 'all' ? 'bg-white shadow-sm text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}>
          <UsersIcon className="w-4 h-4" /> Tüm Kullanıcılar
        </button>
        <button onClick={() => setActiveTab('admins')} className={`flex items-center gap-2 px-5 py-2.5 rounded-lg font-medium text-sm transition-all ${activeTab === 'admins' ? 'bg-white shadow-sm text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}>
          <ShieldCheck className="w-4 h-4" /> Yönetici Seçkisi
        </button>
        <button onClick={() => setActiveTab('shoppers')} className={`flex items-center gap-2 px-5 py-2.5 rounded-lg font-medium text-sm transition-all ${activeTab === 'shoppers' ? 'bg-white shadow-sm text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}>
          <ShoppingBag className="w-4 h-4" /> Müşteriler
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        {loading ? (
          <div className="p-8 text-center text-gray-500 animate-pulse">Kullanıcılar Yükleniyor...</div>
        ) : users.length === 0 ? (
          <div className="p-8 text-center text-gray-500">Bu kategoride kimse bulunmuyor.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse min-w-[600px]">
              <thead>
                <tr className="bg-gray-50 text-gray-600 text-sm border-b border-gray-100">
                  <th className="p-4 font-semibold">ID</th>
                  <th className="p-4 font-semibold">Kullanıcı Adı</th>
                  <th className="p-4 font-semibold">E-Posta</th>
                  
                  {activeTab === 'shoppers' && (
                     <>
                       <th className="p-4 font-semibold">Ad</th>
                       <th className="p-4 font-semibold">Soyad</th>
                     </>
                  )}
                  {activeTab === 'all' && <th className="p-4 font-semibold">Rol</th>}
                  
                  <th className="p-4 flex justify-end font-semibold text-right">İşlemler</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id} className="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                    <td className="p-4 font-mono text-xs text-gray-500">{user.id}</td>
                    <td className="p-4 font-medium text-gray-800">{user.username}</td>
                    <td className="p-4 text-gray-600">{user.email || '-'}</td>
                    
                    {activeTab === 'shoppers' && (
                       <>
                         <td className="p-4 text-gray-800 font-medium">{user.firstName || '-'}</td>
                         <td className="p-4 text-gray-800 font-medium">{user.lastName || '-'}</td>
                       </>
                    )}

                    {activeTab === 'all' && (
                       <td className="p-4">
                         {user.userType && user.userType.map((t: any, idx: number) => (
                           <span key={idx} className={`px-2 py-1 text-xs font-medium rounded-full mr-1 inline-block ${
                             t.userType === 'ADMIN' ? 'bg-red-100 text-red-700' :
                             t.userType === 'SELLER' ? 'bg-orange-100 text-orange-700' :
                             'bg-blue-100 text-blue-700'
                           }`}>
                             {t.userType}
                           </span>
                         ))}
                       </td>
                    )}

                    <td className="p-4 flex justify-end gap-2">
                      {activeTab === 'shoppers' ? (
                          <button onClick={() => openShopperEditModal(user.id)} className="p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" title="Müşteri Detay Düzenleme">
                             <Edit className="w-5 h-5" />
                          </button>
                      ) : (
                          <button onClick={() => openEditModal(user)} className="p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" title="Genel Düzenleme">
                             <Edit className="w-5 h-5" />
                          </button>
                      )}

                      <button onClick={() => handleDelete(user.id)} className="p-2 text-gray-400 hover:text-red-500 hover:bg-red-50 rounded-lg transition-colors" title="Sistemden Sil">
                        <Trash2 className="w-5 h-5" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {showAdminModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4 animate-in fade-in">
           <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6">
              <h3 className="font-bold text-lg mb-4 text-gray-800">Yeni Admin Ekle</h3>
              <form onSubmit={handleCreateAdmin} className="space-y-3">
                 <input type="text" placeholder="Kullanıcı Adı" required value={formData.username} onChange={e=>setFormData({...formData, username: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="email" placeholder="E-Posta" required value={formData.email} onChange={e=>setFormData({...formData, email: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="password" placeholder="Şifre" required value={formData.password} onChange={e=>setFormData({...formData, password: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <div className="flex justify-end gap-2 mt-4">
                    <button type="button" onClick={() => setShowAdminModal(false)} className="px-4 py-2 text-gray-600 bg-gray-100 rounded">İptal</button>
                    <button type="submit" className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700">Oluştur</button>
                 </div>
              </form>
           </div>
        </div>
      )}

      {showSellerModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4 animate-in fade-in">
           <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6">
              <h3 className="font-bold text-lg mb-4 text-gray-800">Yeni Satıcı Ekle</h3>
              <form onSubmit={handleCreateSeller} className="space-y-3">
                 <input type="text" placeholder="Kullanıcı Adı" required value={formData.username} onChange={e=>setFormData({...formData, username: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="email" placeholder="E-Posta" required value={formData.email} onChange={e=>setFormData({...formData, email: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="password" placeholder="Şifre" required value={formData.password} onChange={e=>setFormData({...formData, password: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="text" placeholder="Şirket Adı" required value={formData.companyName} onChange={e=>setFormData({...formData, companyName: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <div className="flex justify-end gap-2 mt-4">
                    <button type="button" onClick={() => setShowSellerModal(false)} className="px-4 py-2 text-gray-600 bg-gray-100 rounded">İptal</button>
                    <button type="submit" className="px-4 py-2 bg-orange-600 text-white rounded hover:bg-orange-700">Oluştur</button>
                 </div>
              </form>
           </div>
        </div>
      )}

      {showEditModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4 animate-in fade-in">
           <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6">
              <h3 className="font-bold text-lg mb-4 text-gray-800">Kullanıcı (Genel) Düzenle</h3>
              <form onSubmit={handleEditUser} className="space-y-3">
                 <input type="text" placeholder="Kullanıcı Adı" required value={formData.username} onChange={e=>setFormData({...formData, username: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="email" placeholder="E-Posta" required value={formData.email} onChange={e=>setFormData({...formData, email: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <input type="password" placeholder="Yeni Şifre Belirle" required value={formData.password} onChange={e=>setFormData({...formData, password: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 outline-none"/>
                 <div className="flex justify-end gap-2 mt-4">
                    <button type="button" onClick={() => setShowEditModal(false)} className="px-4 py-2 text-gray-600 bg-gray-100 rounded">İptal</button>
                    <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">Güncelle</button>
                 </div>
              </form>
           </div>
        </div>
      )}

      {showShopperEditModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4 animate-in fade-in">
           <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6 border-t-4 border-green-500">
              <h3 className="font-bold text-lg mb-4 text-gray-800">Müşteri (Shopper) Detay Düzenleme</h3>
              <form onSubmit={handleEditShopperDetails} className="space-y-3">
                 <input type="text" placeholder="Ad (First Name)" required value={formData.firstName} onChange={e=>setFormData({...formData, firstName: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 focus:ring-green-500 outline-none"/>
                 <input type="text" placeholder="Soyad (Last Name)" required value={formData.lastName} onChange={e=>setFormData({...formData, lastName: e.target.value})} className="w-full px-3 py-2 border rounded focus:ring-2 focus:ring-green-500 outline-none"/>
                 <div className="flex justify-end gap-2 mt-4">
                    <button type="button" onClick={() => setShowShopperEditModal(false)} className="px-4 py-2 text-gray-600 bg-gray-100 rounded">İptal</button>
                    <button type="submit" className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Müşteriyi Güncelle</button>
                 </div>
              </form>
           </div>
        </div>
      )}
    </div>
  );
};

export default AdminUsers;