import React, { useState } from 'react'
import videoLogo from "../assets/upload.png";
import { Button, Card, Label, Progress, Textarea, TextInput } from 'flowbite-react';
import axios from 'axios';
function VideoUpload() {


  const [selectfile, setSelectfile] = useState(null);
  const [progess, setProgess] = useState(0);
  const [UploadProgress, setUploadProgress] = useState(false);
  const [error, setError] = useState(null);
  const[metavideo,setMetaVideoData] = useState(
{
title:"",

} );










function formfiledChange(event){

setMetaVideoData({
  ...metavideo,
  [event.target.name]: event.target.value
})
   
}

  function handelFileChange(event) {
    setSelectfile(event.target.files[0])
  }

// From Handel part   
function handelFormSubmit(formEvent) {
  formEvent.preventDefault();

  sendVideoToServer(selectfile,metavideo)
 
} 



//send Video to server 
async function sendVideoToServer(){
  setUploadProgress(true);
 

  try {

    let formData  = new FormData();
    formData.append("title",metavideo.title);
    formData.append("file", selectfile);
    


     let respone = await axios.post(`http://localhost:8090/api/v1/`,formData,{
       headers:
       {
        "Content-Type": "multipartfile/form-data",


       }, 
       onUploadProgress: (progressEvent) => {
        const { loaded, total } = progressEvent;
        let progress = Math.floor((loaded * 100) / total);
        setUploadProgress(progress);  // Update the progress
      },

  
    });

     setUploadProgress(false);
  
    
    
  } catch (error) {
    console.log(error);
    setUploadProgress(false);
    
  }

}












  return (
    <>
      <div className='flex  justify-center items-center '>

        <Card className="max-w-1/4 m-0 p-0 text-left font-light font-mono ">
          <h1 className='text-3xl dark:text-white'> Upload Video</h1>
          <div className='py-0'>
            <form onSubmit={handelFormSubmit} className="flex    space-y-5  flex-col py-0" action='#'>
              
              
            <div className='text-left '>
                <div className='mb-2 block'>
                <Label> Video Title</Label>
                </div>
                <TextInput onChange={formfiledChange} className="w-96  mb-4"  id='filename' type='text' name='title' placeholder='please tell file name '/>
              
              
                </div>



              <div className=' flex justify-center flex-row mt-2 mb-7'>
                <div className=" shrink-0 pr-6">
                  <img className="h-14 w-14 object-cover " src={videoLogo} alt="Current profile photo" />
                </div>
                <label className="block py-2">
                  <span className="sr-only">Choose profile photo</span>
                  <input
                  name='file'
                    onChange={handelFileChange}
                    type="file" className="block w-full text-sm text-slate-500
                   file:mr-4 file:py-2 file:px-4
                   file:rounded-full file:border-0
                   file:text-sm file:font-semibold
                 file:bg-violet-50 file:text-violet-700
                   hover:file:bg-violet-100 "/>

                </label>
              </div>

              <div className='  flex justify-center text-center items-center'>
              <Progress 
              hidden={!UploadProgress}
              progress={progess}
              progressLabelPosition="inside"
              textLabel="Uploading..."
              textLabelPosition="outside"
              size="lg"
              className='w-96'
              labelProgress
              labelText/>

              </div>

              <div className=' flex justify-center items-center'>
                <Button disabled={UploadProgress} className='w-40' size='lg' type='submit'  >Upload</Button>
              </div>






            </form>
          </div>

        </Card>
      </div>
    </>
  );
}
export default VideoUpload
